package jp.dip.oyasirazu.ws.voiceroid.yukari;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.servlet.ServletContext;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.MTOM;

import jp.dip.oyasirazu.simplecommand.command.Command;
import jp.dip.oyasirazu.simplecommand.command.CommandException;

@MTOM
@WebService
@SOAPBinding(style = Style.RPC)
public class Yukari {

    @Resource
    private WebServiceContext webServiceContext;
    private ServletContext servletContext = null;
    private String contextPath = null;
    private String voiceroidPath = null;
    private String lamePath = null;
    private String tempWavPath = null;
    private String tempMp3Path = null;
    

    @WebMethod
    public YukariResponse echo(@WebParam(name = "message") String message) {

        // 開始ログ
        System.out.println("start echo");

        System.out.println(new File(".").getAbsolutePath());

        // 必要なら初期化
        if (servletContext == null) {
            init();
        }

        File tempVoiceFile = null;
        try {
            
            // Voiceroid 実行
            executeVoiceroid(message);

            // 返却するファイルのパスを格納する変数
            String tempVoicePath;

            // lame の存在確認
            YukariResponse response = new YukariResponse();
            if (new File(lamePath).exists()) {
                System.out.println("execute lame.");
                executeLame();
                tempVoicePath = tempMp3Path;
                response.setType("mp3");
            } else {
                tempVoicePath = tempWavPath;
                response.setType("wav");
            }

            tempVoiceFile = new File(tempVoicePath);

            int fileSize = (int) tempVoiceFile.length();
            byte[] voice = new byte[fileSize];

            BufferedInputStream bis = null;
            try {
                bis = new BufferedInputStream(
                        new FileInputStream(tempVoiceFile));
                bis.read(voice);
                response.setVoice(voice);
            } catch (IOException e) {
                e.printStackTrace();
                // throw new IOException("io error.", e);
                System.out.println("io error.");
                return new YukariResponse();
            } finally {
                try {
                    if (bis != null) {
                        bis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("close error.");
                }
            }

            tempVoiceFile.delete();
            return response;
        } catch (CommandException e) {
            return new YukariResponse();
        } finally {
            System.out.println("end echo");
        }
    }

    private void init() {
        System.out.println("Start Init.");
        servletContext = (ServletContext) webServiceContext
                .getMessageContext().get(MessageContext.SERVLET_CONTEXT);

        contextPath = servletContext.getRealPath("/");
        voiceroidPath = contextPath
                + "WEB-INF" + File.separator
                + "bin" + File.separator
                + "VoiceroidEcho.exe";

        tempWavPath = contextPath
                + "WEB-INF" + File.separator + "temp.wav";

        tempMp3Path = contextPath
                + "WEB-INF" + File.separator + "temp.mp3";

        lamePath = contextPath
                + "WEB-INF" + File.separator
                + "bin" + File.separator
                + "lame.exe";
    }
    
    private void executeVoiceroid(String message) throws CommandException {
        Command command = new Command(voiceroidPath);
        command.setArgs(message, "-o", tempWavPath, "-s");
        command.execute();
    }
    
    private void executeLame() throws CommandException {
        Command command = new Command(lamePath);
        command.setArgs("-S", tempWavPath);
        command.execute();
    }
    
    public static class YukariResponse {
        private String type;
        private byte[] voice;
        
        public YukariResponse() {
            setType("None");
            setVoice(null);
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public byte[] getVoice() {
            return voice;
        }

        public void setVoice(byte[] voice) {
            this.voice = voice;
        }
    }
}
