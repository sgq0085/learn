package com.gqshao.commons.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class Files {

    private static final Logger logger = LoggerFactory.getLogger(Files.class);

    /**
     * 返回单个文件
     */
    public static File saveFile(HttpServletRequest request) {
        return saveFile(request, null);
    }


    /**
     * 返回单个文件
     */
    public static File saveFile(HttpServletRequest request, File dir) {
        List<File> files = uploadFile(request, dir);
        if (CollectionUtils.isNotEmpty(files)) {
            return files.get(0);
        }
        return null;
    }

    /**
     * 上传文件
     *
     * @return 返回文件集合
     */
    public static List<File> uploadFile(HttpServletRequest request) {
        return uploadFile(request, null);
    }

    /**
     * 上传文件
     *
     * @param request HttpRequest
     * @param dir     目标目录
     * @return 返回文件集合
     */
    public static List<File> uploadFile(HttpServletRequest request, File dir) {
        Map<String, Object> params = enctypeEqualsMultipartFormDataParam(request, dir);
        if (MapUtils.isNotEmpty(params)) {
            return (List<File>) params.get("files");
        } else {
            return null;
        }
    }

    /**
     * 上传文件并返回其他表单参数
     *
     * @param request HttpRequest
     * @param dir     目标目录 为空时自动建立
     * @return Map<String, Object> 结果key1 params:Map<String, String>普通返回值 结果key2 files:List<File>文件队列
     */
    public static Map<String, Object> enctypeEqualsMultipartFormDataParam(HttpServletRequest request, File dir) {
        Map<String, Object> res = Maps.newHashMap();
        String contentType = request.getContentType();
        if (StringUtils.isBlank(contentType) || !(contentType.contains("multipart/form-data") || contentType.contains("multipart/mixed"))) {
            return null;
        }
        String encoding = request.getCharacterEncoding();
        if (dir == null) {
            dir = new File(System.getProperty("java.io.tmpdir") + File.separator + "web_files_temp_dir" + File.separator + UUID.randomUUID().toString().replaceAll("-", ""));
        }
        try {
            FileUtils.forceMkdir(dir);
            ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
            upload.setHeaderEncoding(encoding);
            List<FileItem> itemList = upload.parseRequest(request);
            if (CollectionUtils.isEmpty(itemList)) {
                logger.info("上传文件表单为空");
                return null;
            }
            // 文件集合
            List<File> fileList = Lists.newArrayList();
            Set<String> fileMd5Set = Sets.newHashSet();
            Set<Long> sizeSet = Sets.newHashSet();
            //普通字段
            Map<String, String> params = Maps.newHashMap();
            for (FileItem item : itemList) {
                String filename = item.getName();
                if (!item.isFormField() && StringUtils.isNotBlank(filename)) {
                    // 在文件名中加入时间戳、防止文件名重复
                    File file = new File(dir.getAbsolutePath() + File.separator + filename);
                    InputStream is = null;
                    OutputStream os = null;
                    try {
                        is = item.getInputStream();
                        os = new FileOutputStream(file);
                        byte[] bytes = new byte[2048];
                        int length;
                        while ((length = is.read(bytes)) != -1) {
                            os.write(bytes, 0, length);
                        }
                        os.flush();
                    } finally {
                        IOUtils.closeQuietly(is);
                        IOUtils.closeQuietly(os);
                    }
                    // 检查文件MD5，作为去重依据
                    int setSize = sizeSet.size();
                    String md5 = getFileMD5(file);
                    fileMd5Set.add(md5);
                    if (fileMd5Set.size() == setSize) {
                        file.delete();
                    } else {
                        fileList.add(file);
                    }
                } else if (item.isFormField()) {
                    String fieldName = item.getFieldName();
                    String value = item.getString(encoding);
                    if (StringUtils.isNotBlank(fieldName) && StringUtils.isNotBlank(value)) {
                        params.put(fieldName, value);
                    }
                }
            }
            res.put("files", fileList);
            res.put("params", params);
            return res;
        } catch (Exception e) {
            logger.error("上传复合表单出现异常", e);
        }
        return null;
    }

    /**
     * 工具方法 用于下载文件
     *
     * @param file 目标文件
     */
    public static void download(HttpServletRequest request, HttpServletResponse response, File file) {
        download(request, response, file, null, null);
    }

    /**
     * 工具方法 用于下载文件
     *
     * @param file     目标文件
     * @param fileName 文件名
     * @param isDelete 完成下载后是否删除
     */
    public static synchronized Boolean download(HttpServletRequest request, HttpServletResponse response, File file,
                                                String fileName, Boolean isDelete) {
        String agent = request.getHeader("USER-AGENT");
        try {
            InputStream fis = new BufferedInputStream(new FileInputStream(file));
            ServletOutputStream streamOut = response.getOutputStream();
            try {
                response.reset();
                response.setContentType("application/x-msdownload;charset=UTF-8");
                if (StringUtils.isNotBlank(agent) && (agent.contains("MSIE") || agent.contains("like Gecko"))) {
                    if (null == fileName || "".equals(fileName)) {
                        response.setHeader("Content-Disposition", "attachment; filename="
                                + java.net.URLEncoder.encode(file.getName(), "UTF-8").replace("+", "%20"));
                    } else {
                        response.setHeader("Content-Disposition", "attachment; filename="
                                + java.net.URLEncoder.encode(fileName, "UTF-8").replace("+", "%20"));
                    }

                } else {
                    if (null == fileName || "".equals(fileName)) {
                        response.setHeader("Content-Disposition", "attachment; filename=\""
                                + new String((file.getName()).getBytes(), "iso-8859-1") + "\"");
                    } else {
                        response.setHeader("Content-Disposition", "attachment; filename=\""
                                + new String(fileName.getBytes(), "iso-8859-1") + "\"");
                    }

                }
                int bytesRead;
                byte[] buffer = new byte[2048];
                while ((bytesRead = fis.read(buffer)) > 0) {
                    streamOut.write(buffer, 0, bytesRead);
                }
                streamOut.flush();
            } catch (Exception e) {
                logger.error("download ERROR", e);
                return false;
            } finally {
                IOUtils.closeQuietly(streamOut);
                IOUtils.closeQuietly(fis);
                if (isDelete != null && isDelete) {
                    file.delete();
                }
            }
        } catch (Exception e) {
            logger.error("download ERROR", e);
            return false;
        }
        return true;
    }

    /**
     * 工具方法 用于下载文件
     *
     * @param filename 文件名
     * @param content  文件内容
     */
    public static void download(HttpServletRequest request, HttpServletResponse response, String filename,
                                String content) {
        String agent = request.getHeader("USER-AGENT");
        ServletOutputStream streamOut = null;
        try {
            try {
                streamOut = response.getOutputStream();
                response.reset();
                response.setContentType("application/x-msdownload;charset=UTF-8");
                if (StringUtils.isNotBlank(agent) && (agent.contains("MSIE") || agent.contains("like Gecko"))) {
                    response.setHeader("Content-Disposition",
                            "attachment; filename=" + java.net.URLEncoder.encode(filename, "UTF-8").replace("+", "%20"));
                } else {
                    response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(filename.getBytes(), "iso-8859-1") + "\"");
                }
                streamOut.write(content.getBytes());
                streamOut.flush();
            } finally {
                IOUtils.closeQuietly(streamOut);
            }
        } catch (Exception e) {
            logger.error("download ERROR", e);
        }
    }

    /**
     * 压缩文件
     *
     * @param zip   目标文件
     * @param files 原文件
     * @throws IOException
     */
    public static File zip(String zip, File... files) throws IOException {
        return zip(new File(zip), files);
    }

    public static File zip(String zip, List<File> fileList) throws IOException {
        return zip(new File(zip), fileList);
    }

    public static File zip(File zip, List<File> fileList) throws IOException {
        File[] files = new File[fileList.size()];
        for (int i = 0; i < fileList.size(); i++) {
            files[i] = fileList.get(i);
        }
        return zip(zip, files);
    }

    public static File zip(File zip, File... files) {
        try {
            if (zip == null || ArrayUtils.isEmpty(files)) {
                return null;
            }
            zip.getParentFile().mkdirs();
            ZipOutputStream zipOut = null;
            InputStream in = null;
            try {
                zipOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zip)));
                for (int i = 0; i < files.length; i++) {
                    try {
                        // 解析并保存至文件中
                        File resultFile = files[i];
                        in = new BufferedInputStream(new FileInputStream(resultFile));
                        ZipEntry ze = new ZipEntry(resultFile.getName());
                        zipOut.putNextEntry(ze);
                        int len;
                        byte[] buffer = new byte[2048];
                        while ((len = in.read(buffer, 0, 2048)) != -1) {
                            zipOut.write(buffer, 0, len);
                        }
                    } finally {
                        IOUtils.closeQuietly(in);
                    }
                    zipOut.closeEntry();
                }
            } finally {
                IOUtils.closeQuietly(zipOut);
            }
        } catch (Exception e) {
            logger.error("zip ERROR", e);
        }
        return zip;
    }

    /**
     * 解压文件
     */
    public static List<File> unzip(File src) {
        List<File> res = Lists.newArrayList();
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(src);
            Enumeration emu = zipFile.entries();
            int i = 0;
            while (emu.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) emu.nextElement();
                //会把目录作为一个file读出一次，所以只建立目录就可以，之下的文件还会被迭代到。
                if (entry.isDirectory()) {
                    new File(src.getParentFile().getAbsolutePath() + File.separator + entry.getName()).mkdirs();
                    continue;
                }
                BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
                File file = new File(src.getParentFile().getAbsolutePath() + File.separator + entry.getName());
                //加入这个的原因是zipfile读取文件是随机读取的，这就造成可能先读取一个文件
                //而这个文件所在的目录还没有出现过，所以要建出目录来。
                File parent = file.getParentFile();
                if (parent != null && (!parent.exists())) {
                    parent.mkdirs();
                }
                FileOutputStream fos = null;
                BufferedOutputStream bos = null;
                try {
                    fos = new FileOutputStream(file);
                    bos = new BufferedOutputStream(fos, 2048);
                    int count;
                    byte data[] = new byte[2048];
                    while ((count = bis.read(data, 0, 2048)) != -1) {
                        bos.write(data, 0, count);
                    }
                    bos.flush();
                } finally {
                    IOUtils.closeQuietly(bos);
                    IOUtils.closeQuietly(bis);
                }
                res.add(file);
            }
            return res;
        } catch (IOException e) {
            logger.error("unzip ERROR", e);
            return null;
        } finally {
            try {
                zipFile.close();
            } catch (IOException e) {
                logger.error("unzip ERROR", e);
            }
        }

    }


    /**
     * 返回文件扩展名
     *
     * @param file 目标文件
     * @return 文件扩展名
     */
    public static String getFileExtension(File file) {
        String filename = file.getName();
        return getFileExtension(filename);
    }

    public static String getFileExtension(String filename) {
        if (filename.matches(".+\\..+")) {
            return filename.substring(filename.lastIndexOf(".") + 1, filename.length());
        } else {
            return "";
        }
    }

    /**
     * 返回不带路径和扩展名的文件名
     *
     * @param fileName
     * @return
     */
    public static synchronized String getFilenameWithoutExtension(String fileName) {
        fileName = fileName.replaceAll("\\\\", "/").replaceAll("//", "/");
        return fileName.substring(fileName.lastIndexOf("/") == -1 ? 0 : fileName.lastIndexOf("/") + 1,
                fileName.lastIndexOf(".") == -1 ? fileName.length() : fileName.lastIndexOf("."));
    }

    /**
     * 检查文件MD5的值
     *
     * @param file 被检测文件
     * @return 文件MD5值
     */
    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[2048];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
            }
            IOUtils.closeQuietly(in);
        } catch (Exception e) {
            logger.error("getFileMD5 ERROR", e);
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        String md5 = bigInt.toString(16);
        if (md5.length() == 32) {
            return md5;
        }
        for (int i = 32 - md5.length(); i > 0; i--) {
            md5 = "0" + md5;
        }
        return md5;
    }

    /**
     * 清空文件夹
     * <p>如果传入文件夹不存在会被建立</p>
     */
    public static void cleanDirectory(File dir) {
        if (dir != null && dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File f : files) {
                if (f.isDirectory()) {
                    cleanDirectory(f);
                } else {
                    f.delete();
                }
            }
        } else {
            dir.mkdirs();
        }
    }

    /**
     * 遍历文件夹下所有文件
     *
     * @param dir
     * @return
     */
    public static List<File> getFileFromDirectory(File dir) {
        if (dir == null || !dir.exists() || dir.isFile()) {
            return null;
        }
        List<File> result = Lists.newArrayList();
        File[] fileArray = dir.listFiles();
        for (File file : fileArray) {
            if (file.isFile()) {
                result.add(file);
            } else {
                result.containsAll(getFileFromDirectory(file));
            }
        }
        return result;
    }

    /**
     * 拷贝文件
     *
     * @param src  源文件
     * @param dest 目标文件或文件夹
     */
    public static void cp(File src, File dest) {
        if (dest.isDirectory()) {
            dest.mkdirs();
            dest = new File(dest.getAbsoluteFile() + File.separator + src.getName());
        }
        try {
            InputStream is = null;
            OutputStream out = null;
            try {
                is = new BufferedInputStream(new FileInputStream(src));
                out = new FileOutputStream(dest);
                int len;
                byte[] buffer = new byte[2048];
                while ((len = is.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
                out.flush();
            } finally {
                IOUtils.closeQuietly(is);
                IOUtils.closeQuietly(out);
            }
        } catch (Exception e) {
            logger.error("cp ERROR", e);
        }
    }

    /**
     * 通过BufferedRandomAccessFile读取文件,推荐
     *
     * @param file     源文件
     * @param encoding 文件编码
     * @param pos      偏移量
     * @param num      读取量
     * @return values文件内容，pos当前偏移量
     */
    public static Map<String, Object> BufferedRandomAccessFileReadLine(File file, String encoding, long pos, int num) {
        Map<String, Object> res = Maps.newHashMap();
        List<String> values = Lists.newLinkedList();
        res.put("values", values);
        BufferedRandomAccessFile reader = null;
        try {
            reader = new BufferedRandomAccessFile(file, "r");
            reader.seek(pos);
            for (int i = 0; i < num; i++) {
                String pin = reader.readLine();
                if (StringUtils.isBlank(pin)) {
                    break;
                }
                values.add(new String(pin.getBytes("8859_1"), encoding));
            }
            res.put("pos", reader.getFilePointer());
        } catch (Exception e) {
            logger.error("BufferedRandomAccessFileReadLine ERROR", logger);
        } finally {
            IOUtils.closeQuietly(reader);
        }
        return res;
    }

    public static Map<String, Object> BufferedRandomAccessFileReadLine(File file, long pos, int num) {
        return BufferedRandomAccessFileReadLine(file, "UTF-8", pos, num);
    }

    /**
     * 获取文件行数
     */
    public static Integer countLine(File file) {
        if (file == null || !file.exists() || file.isDirectory()) {
            return 0;
        }
        BufferedReader br = null;
        int count = 0;
        try {
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                while (br.readLine() != null) {
                    count++;
                }
            } finally {
                IOUtils.closeQuietly(br);
            }
        } catch (Exception e) {
            return 0;
        }
        return count;
    }

    /**
     * 给定的输入文件中的内容复制到一个新的字节数组
     */
    public static byte[] copyToByteArray(File file) {
        try {
            return FileCopyUtils.copyToByteArray(file);
        } catch (IOException e) {
            logger.error("copyToByteArray ERROR", e);
        }
        return null;
    }

    /**
     * 将字节数组中的内容写入到文件中
     */
    public static void copyToFile(byte[] content, File file) {
        try {
            FileCopyUtils.copy(content, file);
        } catch (IOException e) {
            logger.error("copyToFile ERROR", e);
        }
    }

    /**
     * 合并多个同类型文件到一个新的文件中
     */
    public static File mergeFiles(File destFile, File... sourceFiles) {
        if (destFile.exists() || sourceFiles.length == 0) {
            return destFile;
        }
        DataInputStream dis = null;
        DataOutputStream dos = null;
        try {
            dos = new DataOutputStream(new FileOutputStream(destFile));
            for (File file : sourceFiles) {
                dis = new DataInputStream(new FileInputStream(file));
                long transMaxByte = file.length();
                while (transMaxByte > 0) {
                    byte[] bytes = transMaxByte > 1024 ? new byte[1024] : new byte[(int) transMaxByte];
                    if (dis.read(bytes) != -1) {
                        dos.write(bytes);
                        transMaxByte -= bytes.length;
                    } else {
                        break;
                    }
                }
                dos.flush();
                IOUtils.closeQuietly(dis);
            }
        } catch (Exception e) {
            logger.error("mergeFiles ERROR", e);
        } finally {
            IOUtils.closeQuietly(dos);
            IOUtils.closeQuietly(dis);
        }
        return destFile;
    }

    /**
     * 根据指定的大小将文件分割成若干个文件,并依次输出到list中
     * 需要注意的是，当文件大小为0时，则返回源文件
     */
    public static List<File> cutFile(File srcFile, Integer newFileLength) {
        DataOutputStream dos = null;
        DataInputStream dis = null;
        List<File> res = Lists.newArrayList();
        try {
            if (srcFile == null || srcFile.length() == 0) {
                return res;
            }
            dis = new DataInputStream(new FileInputStream(srcFile));
            Integer fileNum = (int) Math.ceil(srcFile.length() / newFileLength.doubleValue());
            String filePreFix = Files.getFilenameWithoutExtension(srcFile.getName());
            String fileSuffix = Files.getFileExtension(srcFile);
            for (int i = 0; i < fileNum; i++) {
                String tmpFileName = filePreFix + "_" + (i + 1) + "." + fileSuffix;
                File tmpFile = new File(srcFile.getParent(), tmpFileName);
                dos = new DataOutputStream(new FileOutputStream(tmpFile));
                long transMaxByte = (i == fileNum - 1) ? srcFile.length() - (fileNum - 1) * newFileLength : newFileLength;
                while (transMaxByte > 0) {
                    byte[] bytes = transMaxByte > 1024 ? new byte[1024] : new byte[(int) transMaxByte];
                    if (dis.read(bytes) != -1) {
                        dos.write(bytes);
                        transMaxByte -= bytes.length;
                    } else {
                        break;
                    }
                }
                dos.flush();
                res.add(tmpFile);
                IOUtils.closeQuietly(dos);
            }
        } catch (Exception e) {
            logger.error("cutFile ERROR", e);
        } finally {
            IOUtils.closeQuietly(dis);
            IOUtils.closeQuietly(dos);
        }
        return res;
    }
}