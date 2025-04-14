package dev.mikoto2000.study.spring.integration.sftp.configuration;

import java.io.File;
import java.util.Arrays;

import org.apache.sshd.sftp.client.SftpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.sftp.dsl.Sftp;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;

/**
 * SftpConfiguration
 */
@Configuration
@EnableIntegration
public class SftpConfiguration {

    @Bean
    public SessionFactory<SftpClient.DirEntry> sftpInboundSessionFactory() {
        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory(true);
        factory.setHost("sftp");
        factory.setPort(22);
        factory.setUser("sendcompany");
        factory.setPrivateKey(new FileSystemResource("../config/sftp/sendcompany/.ssh/id_rsa"));
        factory.setAllowUnknownKeys(true);
        return new CachingSessionFactory<>(factory);
    }

    @Bean
    public IntegrationFlow sftpInboundFlow() {
        return IntegrationFlow
                .from(Sftp.inboundAdapter(sftpInboundSessionFactory())
                        .preserveTimestamp(true)
                        .remoteDirectory("/home/sendcompany")
                        .localDirectory(new File("./dir/sendcompany"))
                        .autoCreateLocalDirectory(true)
                        .deleteRemoteFiles(false)
                        .filter(files -> Arrays.stream(files).filter(entry -> entry.getFilename().endsWith(".csv")).toList()),
                        e -> e.poller(Pollers.fixedDelay(5000))) // 5秒おきにポーリング
                .handle(m -> {
                    // 受信したファイルの処理
                    System.out.println("Received: " + ((File)m.getPayload()).getName());
                })
                .get();
    }
}
