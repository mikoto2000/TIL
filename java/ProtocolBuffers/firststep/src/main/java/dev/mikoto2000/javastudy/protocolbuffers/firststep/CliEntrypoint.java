package dev.mikoto2000.javastudy.protocolbuffers.firststep;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.google.protobuf.InvalidProtocolBufferException;

import dev.mikoto2000.javastudy.protocolbuffers.firststep.model.MemberInfoOuterClass.CommonInfo;
import dev.mikoto2000.javastudy.protocolbuffers.firststep.model.MemberInfoOuterClass.Member;
import dev.mikoto2000.javastudy.protocolbuffers.firststep.model.MemberInfoOuterClass.MemberInfo;
import dev.mikoto2000.javastudy.protocolbuffers.firststep.model.MemberInfoOuterClass.StudentProps;
import dev.mikoto2000.javastudy.protocolbuffers.firststep.model.MemberInfoOuterClass.TeacherProps;

/**
 * CliEntrypoint
 */
@Component
@Profile("!test")
public class CliEntrypoint implements CommandLineRunner {
	@Override
	public void run(String... args) throws InvalidProtocolBufferException {
		// MemberInfo の組み立て
		MemberInfo.Builder builder = MemberInfo.newBuilder();
		MemberInfo memberInfo = builder
			.setCommonInfo(CommonInfo.newBuilder().setTimestamp("1234567890").build())
			.addMember(Member.newBuilder()
					.setType("student")
					.setStudentProps(StudentProps.newBuilder()
						.setStudentId("mikoto2000")
						.build())
					.build())
			.addMember(Member.newBuilder()
					.setType("teacher")
					.setTeacherProps(TeacherProps.newBuilder()
						.setTeacherId("makoto2000")
						.build())
					.build())
			.build();

		System.out.printf("memberInfo: %s\n", memberInfo);

		// エンコード
		byte[] memberInfoBytes = memberInfo.toByteArray();

		// デコード
		MemberInfo memberInfoFromByteArray = MemberInfo.parseFrom(memberInfoBytes);

		System.out.printf("memberInfoFromByteArray: %s\n", memberInfoFromByteArray);

		// エンコード前後の結果比較
		System.out.printf("memberInfo.toString().equals(memberInfoFromByteArray.toString()): %s\n", memberInfo.toString().equals(memberInfoFromByteArray.toString()));
	}
}
