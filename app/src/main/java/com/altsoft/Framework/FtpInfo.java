package com.altsoft.Framework;

import android.os.AsyncTask;
import android.util.Log;

import com.groupbyinc.common.apache.commons.net.ftp.FTP;
import com.groupbyinc.common.apache.commons.net.ftp.FTPClient;
import com.groupbyinc.common.apache.commons.net.ftp.FTPFile;
import com.groupbyinc.common.apache.commons.net.ftp.FTPReply;
import com.groupbyinc.common.apache.commons.net.ftp.FTPSClient;
import com.groupbyinc.common.apache.commons.net.util.TrustManagerUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import javax.net.ssl.SSLContext;

/**
 * FTP 업로드
 *
 * @author 전상훈
 * @version 1.0.0
 * @since 2019-03-06 오전 11:27
 **/

public class FtpInfo {
    public FTPSClient ftp;
    private final String TAG = "Connect FTP";
    private static  String Host = "106.246.255.132";
    private static  String USER_ID = "ftpfiles";
    private static  String PW = "altsoft!@34";
    private static  Integer Port = 27021;

    public FtpInfo() throws Exception {

    }

    public FtpInfo(String host, String userid, String pwd, int port) throws Exception {

        if(ftp == null) ftp = new FTPSClient("TLS", false);

        Host = host;
        USER_ID = userid;
        PW = pwd;
        Port = port;
       // ftpConnect(host, user, pwd, port);
    }

    ///
    // FTP 서버와 연결
    public boolean ftpConnect(String host, String userid, String pwd, int port) {

        //System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");

        boolean result = false;
        try {
            if (ftp == null) ftp = new FTPSClient("TLS", false);
            ftp.setTrustManager(TrustManagerUtils.getAcceptAllTrustManager());
            ftp.connect(host, port);

            if (FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                result = ftp.login(userid, pwd);
                ftp.setControlEncoding("UTF-8");
                ftp.setFileType(FTP.BINARY_FILE_TYPE);
                ftp.setFileTransferMode(FTP.BINARY_FILE_TYPE);
                ftp.enterLocalPassiveMode();
                if (ftp.getReplyCode() == 230) {
                    ftp.sendCommand("OPTS UTF8 ON");
                    ftp.execPBSZ(0);
                    ftp.execPROT("P");
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "Couldn't connect to host");
        }
        return result;
    }

    /// FTP 서버와 연결 끊기
    public boolean ftpDisconnect() {
        boolean result = false;
        try {
            ftp.logout();
            ftp.disconnect();
            result = true;
        } catch (Exception e) {
            Log.d(TAG, "Failed to disconnect with server");
        }
        return result;
    }

    ///현재 작업 경로 가져오기
    public String ftpGetDirectory() {
        String directory = null;
        try {
            directory = ftp.printWorkingDirectory();
        } catch (Exception e) {
            Log.d(TAG, "Couldn't get current directory");
        }
        return directory;
    }

    /// 작업 경로 수정
    public boolean ftpChangeDirctory(String directory) {
        try {
            ftp.changeWorkingDirectory(directory);

            return true;
        } catch (Exception e) {
            Log.d(TAG, "Couldn't change the directory");
        }
        return false;
    }

    ///directory 내 파일 리스트 가져오기
    public String[] ftpGetFileList(String directory) {
        String[] fileList = null;
        int i = 0;
        try {
            FTPFile[] ftpFiles = ftp.listFiles(directory);
            fileList = new String[ftpFiles.length];
            for (FTPFile file : ftpFiles) {
                String fileName = file.getName();
                if (file.isFile()) {
                    fileList[i] = "(File) " + fileName;
                } else {
                    fileList[i] = "(Directory) " + fileName;
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileList;
    }

    /// 새로운 directory 생성 및 삭제
    public boolean ftpCreateDirectory(String directory) {
        boolean result = false;
        try {
            result = ftp.makeDirectory(directory);
        } catch (Exception e) {
            Log.d(TAG, "Couldn't make the directory");
        }
        return result;
    }

    public boolean ftpDeleteDirectory(String directory) {
        boolean result = false;
        try {
            result = ftp.removeDirectory(directory);
        } catch (Exception e) {
            Log.d(TAG, "Couldn't remove directory");
        }
        return result;
    }

    ///파일 삭제
    public boolean ftpDeleteFile(String file) {
        boolean result = false;
        try {
            result = ftp.deleteFile(file);
        } catch (Exception e) {
            Log.d(TAG, "Couldn't remove the file");
        }
        return result;
    }

    ///파일 이름 변경
    public boolean ftpRenameFile(String from, String to) {
        boolean result = false;
        try {
            result = ftp.rename(from, to);
        } catch (Exception e) {
            Log.d(TAG, "Couldn't rename file");
        }
        return result;
    }

    //// 파일 다운로드
    public boolean ftpDownloadFile(String srcFilePath, String desFilePath) {
        boolean result = false;
        try {
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.setFileTransferMode(FTP.BINARY_FILE_TYPE);

            FileOutputStream fos = new FileOutputStream(desFilePath);
            result = ftp.retrieveFile(srcFilePath, fos);
            fos.close();
        } catch (Exception e) {
            Log.d(TAG, "Download failed");
        }
        return result;
    }

    /**
     * 폴더존재 유무 확인
     *
     * @author 전상훈
     * @version 1.0.0
     * @since 2019-03-06 오후 1:56
     **/
    public boolean isDirectory(String directory) {
        try {
            if (ftp.cwd(directory) == 550) {
                return false;
            } else if (ftp.cwd(directory) == 250) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 파일존재 유무 확인
     */
    public boolean isFile(String fileName) throws IOException {
        String[] files = ftp.listNames();

        return Arrays.asList(files).contains(fileName);
    }

    /// FTP 서버에 파일 업로드
    public boolean ftpUploadFile(String srcFilePath, String desFileName, String desDirectory) {
        ftpConnect(Host, USER_ID, PW, Port);
        boolean result = false;
        if(desDirectory.indexOf("/")< 0) desDirectory = "/"+ desDirectory;
        try {
            FileInputStream fis = new FileInputStream(srcFilePath);

            if (!isDirectory(desDirectory)) ftpCreateDirectory(desDirectory);

            if (ftpChangeDirctory(desDirectory)) {
                ftp.setUseEPSVwithIPv4(true);
                if(ftp.storeFile(desDirectory + "/" + desFileName, fis)){
                    result = true;
                }
            }
            fis.close();


        } catch (Exception e) {
            Log.d(TAG, "Couldn't upload the file");
        }
        ftpDisconnect();
        return result;
    }


}
