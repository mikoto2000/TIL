import * as $protobuf from "protobufjs";
import Long = require("long");
/** Properties of a CommonInfo. */
export interface ICommonInfo {

    /** CommonInfo timestamp */
    timestamp?: (string|null);
}

/** Represents a CommonInfo. */
export class CommonInfo implements ICommonInfo {

    /**
     * Constructs a new CommonInfo.
     * @param [properties] Properties to set
     */
    constructor(properties?: ICommonInfo);

    /** CommonInfo timestamp. */
    public timestamp: string;

    /**
     * Creates a new CommonInfo instance using the specified properties.
     * @param [properties] Properties to set
     * @returns CommonInfo instance
     */
    public static create(properties?: ICommonInfo): CommonInfo;

    /**
     * Encodes the specified CommonInfo message. Does not implicitly {@link CommonInfo.verify|verify} messages.
     * @param message CommonInfo message or plain object to encode
     * @param [writer] Writer to encode to
     * @returns Writer
     */
    public static encode(message: ICommonInfo, writer?: $protobuf.Writer): $protobuf.Writer;

    /**
     * Encodes the specified CommonInfo message, length delimited. Does not implicitly {@link CommonInfo.verify|verify} messages.
     * @param message CommonInfo message or plain object to encode
     * @param [writer] Writer to encode to
     * @returns Writer
     */
    public static encodeDelimited(message: ICommonInfo, writer?: $protobuf.Writer): $protobuf.Writer;

    /**
     * Decodes a CommonInfo message from the specified reader or buffer.
     * @param reader Reader or buffer to decode from
     * @param [length] Message length if known beforehand
     * @returns CommonInfo
     * @throws {Error} If the payload is not a reader or valid buffer
     * @throws {$protobuf.util.ProtocolError} If required fields are missing
     */
    public static decode(reader: ($protobuf.Reader|Uint8Array), length?: number): CommonInfo;

    /**
     * Decodes a CommonInfo message from the specified reader or buffer, length delimited.
     * @param reader Reader or buffer to decode from
     * @returns CommonInfo
     * @throws {Error} If the payload is not a reader or valid buffer
     * @throws {$protobuf.util.ProtocolError} If required fields are missing
     */
    public static decodeDelimited(reader: ($protobuf.Reader|Uint8Array)): CommonInfo;

    /**
     * Verifies a CommonInfo message.
     * @param message Plain object to verify
     * @returns `null` if valid, otherwise the reason why it is not
     */
    public static verify(message: { [k: string]: any }): (string|null);

    /**
     * Creates a CommonInfo message from a plain object. Also converts values to their respective internal types.
     * @param object Plain object
     * @returns CommonInfo
     */
    public static fromObject(object: { [k: string]: any }): CommonInfo;

    /**
     * Creates a plain object from a CommonInfo message. Also converts values to other types if specified.
     * @param message CommonInfo
     * @param [options] Conversion options
     * @returns Plain object
     */
    public static toObject(message: CommonInfo, options?: $protobuf.IConversionOptions): { [k: string]: any };

    /**
     * Converts this CommonInfo to JSON.
     * @returns JSON object
     */
    public toJSON(): { [k: string]: any };

    /**
     * Gets the default type url for CommonInfo
     * @param [typeUrlPrefix] your custom typeUrlPrefix(default "type.googleapis.com")
     * @returns The default type url
     */
    public static getTypeUrl(typeUrlPrefix?: string): string;
}

/** Properties of a TeacherProps. */
export interface ITeacherProps {

    /** TeacherProps teacherId */
    teacherId?: (string|null);
}

/** Represents a TeacherProps. */
export class TeacherProps implements ITeacherProps {

    /**
     * Constructs a new TeacherProps.
     * @param [properties] Properties to set
     */
    constructor(properties?: ITeacherProps);

    /** TeacherProps teacherId. */
    public teacherId: string;

    /**
     * Creates a new TeacherProps instance using the specified properties.
     * @param [properties] Properties to set
     * @returns TeacherProps instance
     */
    public static create(properties?: ITeacherProps): TeacherProps;

    /**
     * Encodes the specified TeacherProps message. Does not implicitly {@link TeacherProps.verify|verify} messages.
     * @param message TeacherProps message or plain object to encode
     * @param [writer] Writer to encode to
     * @returns Writer
     */
    public static encode(message: ITeacherProps, writer?: $protobuf.Writer): $protobuf.Writer;

    /**
     * Encodes the specified TeacherProps message, length delimited. Does not implicitly {@link TeacherProps.verify|verify} messages.
     * @param message TeacherProps message or plain object to encode
     * @param [writer] Writer to encode to
     * @returns Writer
     */
    public static encodeDelimited(message: ITeacherProps, writer?: $protobuf.Writer): $protobuf.Writer;

    /**
     * Decodes a TeacherProps message from the specified reader or buffer.
     * @param reader Reader or buffer to decode from
     * @param [length] Message length if known beforehand
     * @returns TeacherProps
     * @throws {Error} If the payload is not a reader or valid buffer
     * @throws {$protobuf.util.ProtocolError} If required fields are missing
     */
    public static decode(reader: ($protobuf.Reader|Uint8Array), length?: number): TeacherProps;

    /**
     * Decodes a TeacherProps message from the specified reader or buffer, length delimited.
     * @param reader Reader or buffer to decode from
     * @returns TeacherProps
     * @throws {Error} If the payload is not a reader or valid buffer
     * @throws {$protobuf.util.ProtocolError} If required fields are missing
     */
    public static decodeDelimited(reader: ($protobuf.Reader|Uint8Array)): TeacherProps;

    /**
     * Verifies a TeacherProps message.
     * @param message Plain object to verify
     * @returns `null` if valid, otherwise the reason why it is not
     */
    public static verify(message: { [k: string]: any }): (string|null);

    /**
     * Creates a TeacherProps message from a plain object. Also converts values to their respective internal types.
     * @param object Plain object
     * @returns TeacherProps
     */
    public static fromObject(object: { [k: string]: any }): TeacherProps;

    /**
     * Creates a plain object from a TeacherProps message. Also converts values to other types if specified.
     * @param message TeacherProps
     * @param [options] Conversion options
     * @returns Plain object
     */
    public static toObject(message: TeacherProps, options?: $protobuf.IConversionOptions): { [k: string]: any };

    /**
     * Converts this TeacherProps to JSON.
     * @returns JSON object
     */
    public toJSON(): { [k: string]: any };

    /**
     * Gets the default type url for TeacherProps
     * @param [typeUrlPrefix] your custom typeUrlPrefix(default "type.googleapis.com")
     * @returns The default type url
     */
    public static getTypeUrl(typeUrlPrefix?: string): string;
}

/** Properties of a StudentProps. */
export interface IStudentProps {

    /** StudentProps studentId */
    studentId?: (string|null);
}

/** Represents a StudentProps. */
export class StudentProps implements IStudentProps {

    /**
     * Constructs a new StudentProps.
     * @param [properties] Properties to set
     */
    constructor(properties?: IStudentProps);

    /** StudentProps studentId. */
    public studentId: string;

    /**
     * Creates a new StudentProps instance using the specified properties.
     * @param [properties] Properties to set
     * @returns StudentProps instance
     */
    public static create(properties?: IStudentProps): StudentProps;

    /**
     * Encodes the specified StudentProps message. Does not implicitly {@link StudentProps.verify|verify} messages.
     * @param message StudentProps message or plain object to encode
     * @param [writer] Writer to encode to
     * @returns Writer
     */
    public static encode(message: IStudentProps, writer?: $protobuf.Writer): $protobuf.Writer;

    /**
     * Encodes the specified StudentProps message, length delimited. Does not implicitly {@link StudentProps.verify|verify} messages.
     * @param message StudentProps message or plain object to encode
     * @param [writer] Writer to encode to
     * @returns Writer
     */
    public static encodeDelimited(message: IStudentProps, writer?: $protobuf.Writer): $protobuf.Writer;

    /**
     * Decodes a StudentProps message from the specified reader or buffer.
     * @param reader Reader or buffer to decode from
     * @param [length] Message length if known beforehand
     * @returns StudentProps
     * @throws {Error} If the payload is not a reader or valid buffer
     * @throws {$protobuf.util.ProtocolError} If required fields are missing
     */
    public static decode(reader: ($protobuf.Reader|Uint8Array), length?: number): StudentProps;

    /**
     * Decodes a StudentProps message from the specified reader or buffer, length delimited.
     * @param reader Reader or buffer to decode from
     * @returns StudentProps
     * @throws {Error} If the payload is not a reader or valid buffer
     * @throws {$protobuf.util.ProtocolError} If required fields are missing
     */
    public static decodeDelimited(reader: ($protobuf.Reader|Uint8Array)): StudentProps;

    /**
     * Verifies a StudentProps message.
     * @param message Plain object to verify
     * @returns `null` if valid, otherwise the reason why it is not
     */
    public static verify(message: { [k: string]: any }): (string|null);

    /**
     * Creates a StudentProps message from a plain object. Also converts values to their respective internal types.
     * @param object Plain object
     * @returns StudentProps
     */
    public static fromObject(object: { [k: string]: any }): StudentProps;

    /**
     * Creates a plain object from a StudentProps message. Also converts values to other types if specified.
     * @param message StudentProps
     * @param [options] Conversion options
     * @returns Plain object
     */
    public static toObject(message: StudentProps, options?: $protobuf.IConversionOptions): { [k: string]: any };

    /**
     * Converts this StudentProps to JSON.
     * @returns JSON object
     */
    public toJSON(): { [k: string]: any };

    /**
     * Gets the default type url for StudentProps
     * @param [typeUrlPrefix] your custom typeUrlPrefix(default "type.googleapis.com")
     * @returns The default type url
     */
    public static getTypeUrl(typeUrlPrefix?: string): string;
}

/** Properties of a Member. */
export interface IMember {

    /** Member type */
    type?: (string|null);

    /** Member name */
    name?: (string|null);

    /** Member teacherProps */
    teacherProps?: (ITeacherProps|null);

    /** Member studentProps */
    studentProps?: (IStudentProps|null);
}

/** Represents a Member. */
export class Member implements IMember {

    /**
     * Constructs a new Member.
     * @param [properties] Properties to set
     */
    constructor(properties?: IMember);

    /** Member type. */
    public type: string;

    /** Member name. */
    public name: string;

    /** Member teacherProps. */
    public teacherProps?: (ITeacherProps|null);

    /** Member studentProps. */
    public studentProps?: (IStudentProps|null);

    /** Member properties. */
    public properties?: ("teacherProps"|"studentProps");

    /**
     * Creates a new Member instance using the specified properties.
     * @param [properties] Properties to set
     * @returns Member instance
     */
    public static create(properties?: IMember): Member;

    /**
     * Encodes the specified Member message. Does not implicitly {@link Member.verify|verify} messages.
     * @param message Member message or plain object to encode
     * @param [writer] Writer to encode to
     * @returns Writer
     */
    public static encode(message: IMember, writer?: $protobuf.Writer): $protobuf.Writer;

    /**
     * Encodes the specified Member message, length delimited. Does not implicitly {@link Member.verify|verify} messages.
     * @param message Member message or plain object to encode
     * @param [writer] Writer to encode to
     * @returns Writer
     */
    public static encodeDelimited(message: IMember, writer?: $protobuf.Writer): $protobuf.Writer;

    /**
     * Decodes a Member message from the specified reader or buffer.
     * @param reader Reader or buffer to decode from
     * @param [length] Message length if known beforehand
     * @returns Member
     * @throws {Error} If the payload is not a reader or valid buffer
     * @throws {$protobuf.util.ProtocolError} If required fields are missing
     */
    public static decode(reader: ($protobuf.Reader|Uint8Array), length?: number): Member;

    /**
     * Decodes a Member message from the specified reader or buffer, length delimited.
     * @param reader Reader or buffer to decode from
     * @returns Member
     * @throws {Error} If the payload is not a reader or valid buffer
     * @throws {$protobuf.util.ProtocolError} If required fields are missing
     */
    public static decodeDelimited(reader: ($protobuf.Reader|Uint8Array)): Member;

    /**
     * Verifies a Member message.
     * @param message Plain object to verify
     * @returns `null` if valid, otherwise the reason why it is not
     */
    public static verify(message: { [k: string]: any }): (string|null);

    /**
     * Creates a Member message from a plain object. Also converts values to their respective internal types.
     * @param object Plain object
     * @returns Member
     */
    public static fromObject(object: { [k: string]: any }): Member;

    /**
     * Creates a plain object from a Member message. Also converts values to other types if specified.
     * @param message Member
     * @param [options] Conversion options
     * @returns Plain object
     */
    public static toObject(message: Member, options?: $protobuf.IConversionOptions): { [k: string]: any };

    /**
     * Converts this Member to JSON.
     * @returns JSON object
     */
    public toJSON(): { [k: string]: any };

    /**
     * Gets the default type url for Member
     * @param [typeUrlPrefix] your custom typeUrlPrefix(default "type.googleapis.com")
     * @returns The default type url
     */
    public static getTypeUrl(typeUrlPrefix?: string): string;
}

/** Properties of a MemberInfo. */
export interface IMemberInfo {

    /** MemberInfo commonInfo */
    commonInfo?: (ICommonInfo|null);

    /** MemberInfo member */
    member?: (IMember[]|null);
}

/** Represents a MemberInfo. */
export class MemberInfo implements IMemberInfo {

    /**
     * Constructs a new MemberInfo.
     * @param [properties] Properties to set
     */
    constructor(properties?: IMemberInfo);

    /** MemberInfo commonInfo. */
    public commonInfo?: (ICommonInfo|null);

    /** MemberInfo member. */
    public member: IMember[];

    /**
     * Creates a new MemberInfo instance using the specified properties.
     * @param [properties] Properties to set
     * @returns MemberInfo instance
     */
    public static create(properties?: IMemberInfo): MemberInfo;

    /**
     * Encodes the specified MemberInfo message. Does not implicitly {@link MemberInfo.verify|verify} messages.
     * @param message MemberInfo message or plain object to encode
     * @param [writer] Writer to encode to
     * @returns Writer
     */
    public static encode(message: IMemberInfo, writer?: $protobuf.Writer): $protobuf.Writer;

    /**
     * Encodes the specified MemberInfo message, length delimited. Does not implicitly {@link MemberInfo.verify|verify} messages.
     * @param message MemberInfo message or plain object to encode
     * @param [writer] Writer to encode to
     * @returns Writer
     */
    public static encodeDelimited(message: IMemberInfo, writer?: $protobuf.Writer): $protobuf.Writer;

    /**
     * Decodes a MemberInfo message from the specified reader or buffer.
     * @param reader Reader or buffer to decode from
     * @param [length] Message length if known beforehand
     * @returns MemberInfo
     * @throws {Error} If the payload is not a reader or valid buffer
     * @throws {$protobuf.util.ProtocolError} If required fields are missing
     */
    public static decode(reader: ($protobuf.Reader|Uint8Array), length?: number): MemberInfo;

    /**
     * Decodes a MemberInfo message from the specified reader or buffer, length delimited.
     * @param reader Reader or buffer to decode from
     * @returns MemberInfo
     * @throws {Error} If the payload is not a reader or valid buffer
     * @throws {$protobuf.util.ProtocolError} If required fields are missing
     */
    public static decodeDelimited(reader: ($protobuf.Reader|Uint8Array)): MemberInfo;

    /**
     * Verifies a MemberInfo message.
     * @param message Plain object to verify
     * @returns `null` if valid, otherwise the reason why it is not
     */
    public static verify(message: { [k: string]: any }): (string|null);

    /**
     * Creates a MemberInfo message from a plain object. Also converts values to their respective internal types.
     * @param object Plain object
     * @returns MemberInfo
     */
    public static fromObject(object: { [k: string]: any }): MemberInfo;

    /**
     * Creates a plain object from a MemberInfo message. Also converts values to other types if specified.
     * @param message MemberInfo
     * @param [options] Conversion options
     * @returns Plain object
     */
    public static toObject(message: MemberInfo, options?: $protobuf.IConversionOptions): { [k: string]: any };

    /**
     * Converts this MemberInfo to JSON.
     * @returns JSON object
     */
    public toJSON(): { [k: string]: any };

    /**
     * Gets the default type url for MemberInfo
     * @param [typeUrlPrefix] your custom typeUrlPrefix(default "type.googleapis.com")
     * @returns The default type url
     */
    public static getTypeUrl(typeUrlPrefix?: string): string;
}
