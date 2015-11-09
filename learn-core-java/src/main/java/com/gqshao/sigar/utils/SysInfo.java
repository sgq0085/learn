package com.gqshao.sigar.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hyperic.sigar.*;
import org.hyperic.sigar.cmd.Shell;

import java.util.List;
import java.util.Properties;

public class SysInfo {
    static {
        String var0 = System.getProperty("os.name").toLowerCase();
        if(var0.contains("windows")) {
            System.loadLibrary("sigar-x86-winnt");
        } else {
            System.load("/usr/lib/libsigar-amd64-linux.so");
        }
    }
    // PID
    private String pid;
    // 内核占用详情
    private List<String> cpu;
    // 项目进程CPU运行情况
    private String top;
    // 内存总量
    private String memory;
    // 内存使用量
    private String usedMemory;
    // JVM内存总量
    private String jvmMemory;
    // JVM内存使用量
    private String jvmUsedMemory;
    // 项目当前工作目录
    private String path;
    // 项目所在目录磁盘总量
    private String totalDf;
    // 项目所在目录已用磁盘量
    private String df;

    private SysInfo() {
    }

    public static SysInfo newInstance() {
        SysInfo info = new SysInfo();
        try {
            Sigar sigar = new Sigar();
            // PID
            long[] pids = Shell.getPids(sigar, new String[]{});
            if (ArrayUtils.isNotEmpty(pids)) {
                info.setPid(String.valueOf(pids[0]));
            }
            // CPU使用量
            CpuPerc cpuList[] = sigar.getCpuPercList();
            if (ArrayUtils.isNotEmpty(cpuList)) {
                List<String> cpu = Lists.newArrayList();
                for (CpuPerc cpuPerc : cpuList) {
                    cpu.add(CpuPerc.format(cpuPerc.getCombined()));
                }
                info.setCpu(cpu);
            }
            // 项目CPU使用量
            ProcCpu cpu = sigar.getProcCpu(info.getPid());
            info.setTop(CpuPerc.format(cpu.getPercent()));
            // 内存总量和使用量
            Mem mem = sigar.getMem();
            info.setMemory(mem.getTotal() / 1048576L + "M");
            info.setUsedMemory(mem.getUsed() / 1048576L + "M");
            // JVM内存总量和使用量
            Runtime run = Runtime.getRuntime();
            long max = run.maxMemory();
            long total = run.totalMemory();
            long free = run.freeMemory();
            long used = total - free;
            info.setJvmMemory(max / 1048576L + "M");
            info.setJvmUsedMemory(used / 1048576L + "M");
            // 当前工作目录
            Properties props = System.getProperties();
            info.setPath(props.getProperty("user.dir"));

            // 项目所在目录磁盘总量
            // 项目所在目录已用磁盘量
            FileSystem fslist[] = sigar.getFileSystemList();
            if (ArrayUtils.isNotEmpty(fslist)) {
                String dirName = null;
                String totalDf = null;
                String df = null;
                for (FileSystem fs : fslist) {
                    String tmpName = fs.getDirName(), tmpTotal = null, tmpUsed = null;

                    try {
                        FileSystemUsage usage = sigar.getFileSystemUsage(fs.getDirName());
                        if (2 == fs.getType()) {
                            tmpTotal = usage.getTotal() / 1048576L + "G";
                            tmpUsed = usage.getUsed() / 1048576L + "G";
                        }
                    } catch (Exception e) {
                    }
                    if (StringUtils.isNotBlank(tmpName) && StringUtils.isNotBlank(tmpTotal) && StringUtils.isNotBlank(tmpUsed)
                            && (StringUtils.isBlank(dirName) || dirName.length() < tmpName.length()) && info.getPath().contains(tmpName)) {
                        dirName = tmpName;
                        totalDf = tmpTotal;
                        df = tmpUsed;
                    }
                }
                info.setTotalDf(totalDf);
                info.setDf(df);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public List<String> getCpu() {
        return cpu;
    }

    public void setCpu(List<String> cpu) {
        this.cpu = cpu;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getUsedMemory() {
        return usedMemory;
    }

    public void setUsedMemory(String usedMemory) {
        this.usedMemory = usedMemory;
    }

    public String getJvmMemory() {
        return jvmMemory;
    }

    public void setJvmMemory(String jvmMemory) {
        this.jvmMemory = jvmMemory;
    }

    public String getJvmUsedMemory() {
        return jvmUsedMemory;
    }

    public void setJvmUsedMemory(String jvmUsedMemory) {
        this.jvmUsedMemory = jvmUsedMemory;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTotalDf() {
        return totalDf;
    }

    public void setTotalDf(String totalDf) {
        this.totalDf = totalDf;
    }

    public String getDf() {
        return df;
    }

    public void setDf(String df) {
        this.df = df;
    }
}
