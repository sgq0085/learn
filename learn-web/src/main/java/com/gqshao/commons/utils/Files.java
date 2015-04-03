package com.gqshao.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.*;

public class Files {

    /**
     * 上传文件
     */
    public static List<File> uploadFile(HttpServletRequest request) {
        return uploadFile(request, null);
    }

    /**
     * 上传文件
     *
     * @param request HttpRequest
     * @param dir     目标目录
     * @return List<File> 文件列表
     */
    public static List<File> uploadFile(HttpServletRequest request, File dir) {
        String contentType = request.getContentType();
        if (StringUtils.isBlank(contentType)
                || !(contentType.contains("multipart/form-data") || contentType.contains("multipart/mixed"))) {
            return null;
        }
        String encoding = request.getCharacterEncoding();
        if (dir == null) {
            dir = new File(System.getProperty("java.io.tmpdir") + File.separator + "adp"
                    + File.separator + UUID.randomUUID().toString().replaceAll("-", ""));
        }
        if (!dir.exists()) {
            boolean isSuccess = dir.mkdirs();
            if (!isSuccess) {
                return null;
            }
        }
        try {
            ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
            upload.setHeaderEncoding(encoding);
            List<FileItem> itemList = upload.parseRequest(request);
            List<File> fileList = Lists.newArrayList();
            Set<String> fileMd5Set = Sets.newHashSet();
            Set<Long> sizeSet = Sets.newHashSet();
            for (Iterator<FileItem> it = itemList.iterator(); it.hasNext(); ) {
                FileItem item = it.next();
                String filename = item.getName();
                if (!item.isFormField() && StringUtils.isNotBlank(filename)) {
                    File file = new File(dir + File.separator + getFilenameWithoutExtension(filename) + "."
                            + getFileExtension(filename));
                    InputStream is = null;
                    OutputStream os = null;
                    try {
                        is = item.getInputStream();
                        os = new FileOutputStream(file);
                        byte[] bytes = new byte[1024];
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
                }
            }
            return fileList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 上传文件
     *
     * @param request HttpRequest
     * @param dir     目标目录 为空时自动建立
     *                <p>
     *                返回结果包含两个key，params(<String, String> 普通返回值) 和 files(List<File>文件队列)
     *                </p>
     * @return Map<String, Object>
     */
    public static Map<String, Object> enctypeEqualsMultipartFormData(HttpServletRequest request, File dir) {
        Map<String, Object> res = Maps.newHashMap();
        String contentType = request.getContentType();
        if (StringUtils.isBlank(contentType) || !(contentType.contains("multipart/form-data") || contentType.contains("multipart/mixed"))) {
            return null;
        }
        String encoding = request.getCharacterEncoding();
        if (dir == null) {
            dir = new File(System.getProperty("java.io.tmpdir") + File.separator + "zhw-excel"
                    + File.separator + UUID.randomUUID().toString());
        }
        if (!dir.exists()) {
            boolean isSuccess = dir.mkdirs();
            if (!isSuccess) {
                return null;
            }
        }
        try {
            ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
            upload.setHeaderEncoding(encoding);
            List<FileItem> itemList = upload.parseRequest(request);
            // 文件集合
            List<File> fileList = Lists.newArrayList();
            Set<String> fileMd5Set = Sets.newHashSet();
            Set<Long> sizeSet = Sets.newHashSet();
            //普通字段
            Map<String, String> params = Maps.newHashMap();
            for (Iterator<FileItem> it = itemList.iterator(); it.hasNext(); ) {
                FileItem item = it.next();
                String filename = item.getName();
                if (!item.isFormField() && StringUtils.isNotBlank(filename)) {
                    File file = new File(dir + File.separator + getFilenameWithoutExtension(filename) + "."
                            + getFileExtension(filename));
                    InputStream is = null;
                    OutputStream os = null;
                    try {
                        is = item.getInputStream();
                        os = new FileOutputStream(file);
                        byte[] bytes = new byte[1024];
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
            e.printStackTrace();
        }
        return null;
    }

    public static String saveFile(FileItem item, File dir)
            throws Exception {
        if (dir == null) {
            throw new Exception("when saving file,dir is null");
        }
        if (!dir.exists()) {
            boolean isSuccess = dir.mkdirs();
            if (!isSuccess) {
                return null;
            }
        }

        String filename = item.getName();
        String path = dir + File.separator + getFilenameWithoutExtension(filename) + "." + getFileExtension(filename);

        File file = new File(path);
        if (file.exists()) {
            path = dir + File.separator + getFilenameWithoutExtension(filename) + "-" + new Date().getTime() + "." + getFileExtension(filename);

            file = new File(path);
        }

        InputStream is = null;
        OutputStream os = null;
        try {
            is = item.getInputStream();
            os = new FileOutputStream(file);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = is.read(bytes)) != -1) {
                os.write(bytes, 0, length);
            }
            os.flush();
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(os);
        }

        return path;
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
                if (null != agent && (agent.contains("MSIE") || agent.contains("like Gecko"))) {
                    if (null == fileName || "".equals(fileName)) {
                        response.setHeader(
                                "Content-Disposition",
                                "attachment; filename="
                                        + java.net.URLEncoder.encode(file.getName(), "UTF-8").replace("+", "%20"));
                    } else {
                        response.setHeader("Content-Disposition",
                                "attachment; filename=" + java.net.URLEncoder.encode(fileName, "UTF-8").replace("+", "%20"));
                    }

                } else {
                    if (null == fileName || "".equals(fileName)) {
                        response.setHeader("Content-Disposition",
                                "attachment; filename=\"" + new String((file.getName()).getBytes(), "iso-8859-1") + "\"");
                    } else {
                        response.setHeader("Content-Disposition", "attachment; filename=\""
                                + new String(fileName.getBytes(), "iso-8859-1") + "\"");
                    }

                }
                int bytesRead;
                byte[] buffer = new byte[1024];
                while ((bytesRead = fis.read(buffer, 0, 1024)) > 0) {
                    streamOut.write(buffer, 0, bytesRead);
                }
                streamOut.flush();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                IOUtils.closeQuietly(streamOut);
                IOUtils.closeQuietly(fis);
                if (isDelete != null && isDelete) {
                    file.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
                if (null != agent && agent.contains("MSIE")) {
                    response.setHeader("Content-Disposition",
                            "attachment; filename=" + java.net.URLEncoder.encode(filename, "UTF-8").replace("+", "%20"));
                } else {
                    response.setHeader("Content-Disposition", "attachment; filename=\""
                            + new String(filename.getBytes(), "iso-8859-1") + "\"");
                }
                streamOut.write(content.getBytes());
                streamOut.flush();
            } finally {
                IOUtils.closeQuietly(streamOut);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 工具方法 将文件流输出
     * 如果不传入文件名随机生成32位序列号代替
     */
    public static synchronized Boolean download(HttpServletRequest request, HttpServletResponse response, InputStream is, String fileName) {
        String agent = request.getHeader("USER-AGENT");
        try {
            ServletOutputStream out = response.getOutputStream();
            try {
                response.reset();
                response.setContentType("application/x-msdownload;charset=UTF-8");
                if (null != agent && agent.contains("MSIE")) {

                    if (null == fileName || "".equals(fileName)) {
                        response.setHeader("Content-Disposition", "attachment; filename="
                                + java.net.URLEncoder.encode(UUID.randomUUID().toString(), "UTF-8").replace("+", "%20"));
                    } else {
                        response.setHeader("Content-Disposition",
                                "attachment; filename=" + java.net.URLEncoder.encode(fileName, "UTF-8").replace("+", "%20"));
                    }
                } else {
                    if (null == fileName || "".equals(fileName)) {
                        response.setHeader("Content-Disposition", "attachment; filename=\""
                                + new String(UUID.randomUUID().toString().getBytes(), "iso-8859-1") + "\"");
                    } else {
                        response.setHeader("Content-Disposition", "attachment; filename=\""
                                + new String(fileName.getBytes(), "iso-8859-1") + "\"");
                    }

                }
                int bytesRead;
                byte[] buffer = new byte[1024];
                while ((bytesRead = is.read(buffer, 0, 1024)) > 0) {
                    out.write(buffer, 0, bytesRead);
                }
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                IOUtils.closeQuietly(out);
                IOUtils.closeQuietly(is);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 压缩文件
     *
     * @param zip   目标文件
     * @param files 原文件
     * @throws java.io.IOException
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
            if (ArrayUtils.isEmpty(files)) {
                return null;
            }
            zip.getParentFile().mkdirs();
            ZipOutputStream zipOut = null;
            InputStream in = null;
            try {
                zipOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zip)));
                zipOut.setEncoding("gbk");
                for (int i = 0; i < files.length; i++) {
                    try {
                        // 解析并保存至文件中
                        File resultFile = files[i];
                        in = new BufferedInputStream(new FileInputStream(resultFile));//
                        ZipEntry ze = new ZipEntry(resultFile.getName());
                        zipOut.putNextEntry(ze);
                        int len;
                        byte[] buffer = new byte[1024];
                        while ((len = in.read(buffer, 0, 1024)) != -1) {
                            zipOut.write(buffer, 0, len);
                        }
                        zipOut.flush();
                    } finally {
                        IOUtils.closeQuietly(in);
                    }
                    zipOut.closeEntry();
                }
            } finally {
                IOUtils.closeQuietly(zipOut);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zip;
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
     * 创建临时文件夹
     */
    public static String createTempDir() {
        File tempDir = new File(System.getProperty("java.io.tmpdir") + File.separator
                + UUID.randomUUID().toString().replaceAll("-", ""));
        if (tempDir.exists()) {
            tempDir.delete();
        }
        tempDir.mkdirs();
        return tempDir.getAbsolutePath() + File.separator;
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
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            IOUtils.closeQuietly(in);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }

    /**
     * 清空文件夹
     * <p>如果是文件会被删除,如果文件夹不存在会被建立</p>
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
        if (dir == null || !dir.exists()) {
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
                byte[] buffer = new byte[1024];
                while ((len = is.read(buffer, 0, 1024)) != -1) {
                    out.write(buffer, 0, len);
                }
                out.flush();
            } finally {
                IOUtils.closeQuietly(is);
                IOUtils.closeQuietly(out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}