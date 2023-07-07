/*eslint-disable block-scoped-var, id-length, no-control-regex, no-magic-numbers, no-prototype-builtins, no-redeclare, no-shadow, no-var, sort-vars*/
import * as $protobuf from "protobufjs/minimal";

// Common aliases
const $Reader = $protobuf.Reader, $Writer = $protobuf.Writer, $util = $protobuf.util;

// Exported root namespace
const $root = $protobuf.roots["default"] || ($protobuf.roots["default"] = {});

export const CommonInfo = $root.CommonInfo = (() => {

    /**
     * Properties of a CommonInfo.
     * @exports ICommonInfo
     * @interface ICommonInfo
     * @property {string|null} [timestamp] CommonInfo timestamp
     */

    /**
     * Constructs a new CommonInfo.
     * @exports CommonInfo
     * @classdesc Represents a CommonInfo.
     * @implements ICommonInfo
     * @constructor
     * @param {ICommonInfo=} [properties] Properties to set
     */
    function CommonInfo(properties) {
        if (properties)
            for (let keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                if (properties[keys[i]] != null)
                    this[keys[i]] = properties[keys[i]];
    }

    /**
     * CommonInfo timestamp.
     * @member {string} timestamp
     * @memberof CommonInfo
     * @instance
     */
    CommonInfo.prototype.timestamp = "";

    /**
     * Creates a new CommonInfo instance using the specified properties.
     * @function create
     * @memberof CommonInfo
     * @static
     * @param {ICommonInfo=} [properties] Properties to set
     * @returns {CommonInfo} CommonInfo instance
     */
    CommonInfo.create = function create(properties) {
        return new CommonInfo(properties);
    };

    /**
     * Encodes the specified CommonInfo message. Does not implicitly {@link CommonInfo.verify|verify} messages.
     * @function encode
     * @memberof CommonInfo
     * @static
     * @param {ICommonInfo} message CommonInfo message or plain object to encode
     * @param {$protobuf.Writer} [writer] Writer to encode to
     * @returns {$protobuf.Writer} Writer
     */
    CommonInfo.encode = function encode(message, writer) {
        if (!writer)
            writer = $Writer.create();
        if (message.timestamp != null && Object.hasOwnProperty.call(message, "timestamp"))
            writer.uint32(/* id 1, wireType 2 =*/10).string(message.timestamp);
        return writer;
    };

    /**
     * Encodes the specified CommonInfo message, length delimited. Does not implicitly {@link CommonInfo.verify|verify} messages.
     * @function encodeDelimited
     * @memberof CommonInfo
     * @static
     * @param {ICommonInfo} message CommonInfo message or plain object to encode
     * @param {$protobuf.Writer} [writer] Writer to encode to
     * @returns {$protobuf.Writer} Writer
     */
    CommonInfo.encodeDelimited = function encodeDelimited(message, writer) {
        return this.encode(message, writer).ldelim();
    };

    /**
     * Decodes a CommonInfo message from the specified reader or buffer.
     * @function decode
     * @memberof CommonInfo
     * @static
     * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
     * @param {number} [length] Message length if known beforehand
     * @returns {CommonInfo} CommonInfo
     * @throws {Error} If the payload is not a reader or valid buffer
     * @throws {$protobuf.util.ProtocolError} If required fields are missing
     */
    CommonInfo.decode = function decode(reader, length) {
        if (!(reader instanceof $Reader))
            reader = $Reader.create(reader);
        let end = length === undefined ? reader.len : reader.pos + length, message = new $root.CommonInfo();
        while (reader.pos < end) {
            let tag = reader.uint32();
            switch (tag >>> 3) {
            case 1: {
                    message.timestamp = reader.string();
                    break;
                }
            default:
                reader.skipType(tag & 7);
                break;
            }
        }
        return message;
    };

    /**
     * Decodes a CommonInfo message from the specified reader or buffer, length delimited.
     * @function decodeDelimited
     * @memberof CommonInfo
     * @static
     * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
     * @returns {CommonInfo} CommonInfo
     * @throws {Error} If the payload is not a reader or valid buffer
     * @throws {$protobuf.util.ProtocolError} If required fields are missing
     */
    CommonInfo.decodeDelimited = function decodeDelimited(reader) {
        if (!(reader instanceof $Reader))
            reader = new $Reader(reader);
        return this.decode(reader, reader.uint32());
    };

    /**
     * Verifies a CommonInfo message.
     * @function verify
     * @memberof CommonInfo
     * @static
     * @param {Object.<string,*>} message Plain object to verify
     * @returns {string|null} `null` if valid, otherwise the reason why it is not
     */
    CommonInfo.verify = function verify(message) {
        if (typeof message !== "object" || message === null)
            return "object expected";
        if (message.timestamp != null && message.hasOwnProperty("timestamp"))
            if (!$util.isString(message.timestamp))
                return "timestamp: string expected";
        return null;
    };

    /**
     * Creates a CommonInfo message from a plain object. Also converts values to their respective internal types.
     * @function fromObject
     * @memberof CommonInfo
     * @static
     * @param {Object.<string,*>} object Plain object
     * @returns {CommonInfo} CommonInfo
     */
    CommonInfo.fromObject = function fromObject(object) {
        if (object instanceof $root.CommonInfo)
            return object;
        let message = new $root.CommonInfo();
        if (object.timestamp != null)
            message.timestamp = String(object.timestamp);
        return message;
    };

    /**
     * Creates a plain object from a CommonInfo message. Also converts values to other types if specified.
     * @function toObject
     * @memberof CommonInfo
     * @static
     * @param {CommonInfo} message CommonInfo
     * @param {$protobuf.IConversionOptions} [options] Conversion options
     * @returns {Object.<string,*>} Plain object
     */
    CommonInfo.toObject = function toObject(message, options) {
        if (!options)
            options = {};
        let object = {};
        if (options.defaults)
            object.timestamp = "";
        if (message.timestamp != null && message.hasOwnProperty("timestamp"))
            object.timestamp = message.timestamp;
        return object;
    };

    /**
     * Converts this CommonInfo to JSON.
     * @function toJSON
     * @memberof CommonInfo
     * @instance
     * @returns {Object.<string,*>} JSON object
     */
    CommonInfo.prototype.toJSON = function toJSON() {
        return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
    };

    /**
     * Gets the default type url for CommonInfo
     * @function getTypeUrl
     * @memberof CommonInfo
     * @static
     * @param {string} [typeUrlPrefix] your custom typeUrlPrefix(default "type.googleapis.com")
     * @returns {string} The default type url
     */
    CommonInfo.getTypeUrl = function getTypeUrl(typeUrlPrefix) {
        if (typeUrlPrefix === undefined) {
            typeUrlPrefix = "type.googleapis.com";
        }
        return typeUrlPrefix + "/CommonInfo";
    };

    return CommonInfo;
})();

export const TeacherProps = $root.TeacherProps = (() => {

    /**
     * Properties of a TeacherProps.
     * @exports ITeacherProps
     * @interface ITeacherProps
     * @property {string|null} [teacherId] TeacherProps teacherId
     */

    /**
     * Constructs a new TeacherProps.
     * @exports TeacherProps
     * @classdesc Represents a TeacherProps.
     * @implements ITeacherProps
     * @constructor
     * @param {ITeacherProps=} [properties] Properties to set
     */
    function TeacherProps(properties) {
        if (properties)
            for (let keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                if (properties[keys[i]] != null)
                    this[keys[i]] = properties[keys[i]];
    }

    /**
     * TeacherProps teacherId.
     * @member {string} teacherId
     * @memberof TeacherProps
     * @instance
     */
    TeacherProps.prototype.teacherId = "";

    /**
     * Creates a new TeacherProps instance using the specified properties.
     * @function create
     * @memberof TeacherProps
     * @static
     * @param {ITeacherProps=} [properties] Properties to set
     * @returns {TeacherProps} TeacherProps instance
     */
    TeacherProps.create = function create(properties) {
        return new TeacherProps(properties);
    };

    /**
     * Encodes the specified TeacherProps message. Does not implicitly {@link TeacherProps.verify|verify} messages.
     * @function encode
     * @memberof TeacherProps
     * @static
     * @param {ITeacherProps} message TeacherProps message or plain object to encode
     * @param {$protobuf.Writer} [writer] Writer to encode to
     * @returns {$protobuf.Writer} Writer
     */
    TeacherProps.encode = function encode(message, writer) {
        if (!writer)
            writer = $Writer.create();
        if (message.teacherId != null && Object.hasOwnProperty.call(message, "teacherId"))
            writer.uint32(/* id 1, wireType 2 =*/10).string(message.teacherId);
        return writer;
    };

    /**
     * Encodes the specified TeacherProps message, length delimited. Does not implicitly {@link TeacherProps.verify|verify} messages.
     * @function encodeDelimited
     * @memberof TeacherProps
     * @static
     * @param {ITeacherProps} message TeacherProps message or plain object to encode
     * @param {$protobuf.Writer} [writer] Writer to encode to
     * @returns {$protobuf.Writer} Writer
     */
    TeacherProps.encodeDelimited = function encodeDelimited(message, writer) {
        return this.encode(message, writer).ldelim();
    };

    /**
     * Decodes a TeacherProps message from the specified reader or buffer.
     * @function decode
     * @memberof TeacherProps
     * @static
     * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
     * @param {number} [length] Message length if known beforehand
     * @returns {TeacherProps} TeacherProps
     * @throws {Error} If the payload is not a reader or valid buffer
     * @throws {$protobuf.util.ProtocolError} If required fields are missing
     */
    TeacherProps.decode = function decode(reader, length) {
        if (!(reader instanceof $Reader))
            reader = $Reader.create(reader);
        let end = length === undefined ? reader.len : reader.pos + length, message = new $root.TeacherProps();
        while (reader.pos < end) {
            let tag = reader.uint32();
            switch (tag >>> 3) {
            case 1: {
                    message.teacherId = reader.string();
                    break;
                }
            default:
                reader.skipType(tag & 7);
                break;
            }
        }
        return message;
    };

    /**
     * Decodes a TeacherProps message from the specified reader or buffer, length delimited.
     * @function decodeDelimited
     * @memberof TeacherProps
     * @static
     * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
     * @returns {TeacherProps} TeacherProps
     * @throws {Error} If the payload is not a reader or valid buffer
     * @throws {$protobuf.util.ProtocolError} If required fields are missing
     */
    TeacherProps.decodeDelimited = function decodeDelimited(reader) {
        if (!(reader instanceof $Reader))
            reader = new $Reader(reader);
        return this.decode(reader, reader.uint32());
    };

    /**
     * Verifies a TeacherProps message.
     * @function verify
     * @memberof TeacherProps
     * @static
     * @param {Object.<string,*>} message Plain object to verify
     * @returns {string|null} `null` if valid, otherwise the reason why it is not
     */
    TeacherProps.verify = function verify(message) {
        if (typeof message !== "object" || message === null)
            return "object expected";
        if (message.teacherId != null && message.hasOwnProperty("teacherId"))
            if (!$util.isString(message.teacherId))
                return "teacherId: string expected";
        return null;
    };

    /**
     * Creates a TeacherProps message from a plain object. Also converts values to their respective internal types.
     * @function fromObject
     * @memberof TeacherProps
     * @static
     * @param {Object.<string,*>} object Plain object
     * @returns {TeacherProps} TeacherProps
     */
    TeacherProps.fromObject = function fromObject(object) {
        if (object instanceof $root.TeacherProps)
            return object;
        let message = new $root.TeacherProps();
        if (object.teacherId != null)
            message.teacherId = String(object.teacherId);
        return message;
    };

    /**
     * Creates a plain object from a TeacherProps message. Also converts values to other types if specified.
     * @function toObject
     * @memberof TeacherProps
     * @static
     * @param {TeacherProps} message TeacherProps
     * @param {$protobuf.IConversionOptions} [options] Conversion options
     * @returns {Object.<string,*>} Plain object
     */
    TeacherProps.toObject = function toObject(message, options) {
        if (!options)
            options = {};
        let object = {};
        if (options.defaults)
            object.teacherId = "";
        if (message.teacherId != null && message.hasOwnProperty("teacherId"))
            object.teacherId = message.teacherId;
        return object;
    };

    /**
     * Converts this TeacherProps to JSON.
     * @function toJSON
     * @memberof TeacherProps
     * @instance
     * @returns {Object.<string,*>} JSON object
     */
    TeacherProps.prototype.toJSON = function toJSON() {
        return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
    };

    /**
     * Gets the default type url for TeacherProps
     * @function getTypeUrl
     * @memberof TeacherProps
     * @static
     * @param {string} [typeUrlPrefix] your custom typeUrlPrefix(default "type.googleapis.com")
     * @returns {string} The default type url
     */
    TeacherProps.getTypeUrl = function getTypeUrl(typeUrlPrefix) {
        if (typeUrlPrefix === undefined) {
            typeUrlPrefix = "type.googleapis.com";
        }
        return typeUrlPrefix + "/TeacherProps";
    };

    return TeacherProps;
})();

export const StudentProps = $root.StudentProps = (() => {

    /**
     * Properties of a StudentProps.
     * @exports IStudentProps
     * @interface IStudentProps
     * @property {string|null} [studentId] StudentProps studentId
     */

    /**
     * Constructs a new StudentProps.
     * @exports StudentProps
     * @classdesc Represents a StudentProps.
     * @implements IStudentProps
     * @constructor
     * @param {IStudentProps=} [properties] Properties to set
     */
    function StudentProps(properties) {
        if (properties)
            for (let keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                if (properties[keys[i]] != null)
                    this[keys[i]] = properties[keys[i]];
    }

    /**
     * StudentProps studentId.
     * @member {string} studentId
     * @memberof StudentProps
     * @instance
     */
    StudentProps.prototype.studentId = "";

    /**
     * Creates a new StudentProps instance using the specified properties.
     * @function create
     * @memberof StudentProps
     * @static
     * @param {IStudentProps=} [properties] Properties to set
     * @returns {StudentProps} StudentProps instance
     */
    StudentProps.create = function create(properties) {
        return new StudentProps(properties);
    };

    /**
     * Encodes the specified StudentProps message. Does not implicitly {@link StudentProps.verify|verify} messages.
     * @function encode
     * @memberof StudentProps
     * @static
     * @param {IStudentProps} message StudentProps message or plain object to encode
     * @param {$protobuf.Writer} [writer] Writer to encode to
     * @returns {$protobuf.Writer} Writer
     */
    StudentProps.encode = function encode(message, writer) {
        if (!writer)
            writer = $Writer.create();
        if (message.studentId != null && Object.hasOwnProperty.call(message, "studentId"))
            writer.uint32(/* id 1, wireType 2 =*/10).string(message.studentId);
        return writer;
    };

    /**
     * Encodes the specified StudentProps message, length delimited. Does not implicitly {@link StudentProps.verify|verify} messages.
     * @function encodeDelimited
     * @memberof StudentProps
     * @static
     * @param {IStudentProps} message StudentProps message or plain object to encode
     * @param {$protobuf.Writer} [writer] Writer to encode to
     * @returns {$protobuf.Writer} Writer
     */
    StudentProps.encodeDelimited = function encodeDelimited(message, writer) {
        return this.encode(message, writer).ldelim();
    };

    /**
     * Decodes a StudentProps message from the specified reader or buffer.
     * @function decode
     * @memberof StudentProps
     * @static
     * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
     * @param {number} [length] Message length if known beforehand
     * @returns {StudentProps} StudentProps
     * @throws {Error} If the payload is not a reader or valid buffer
     * @throws {$protobuf.util.ProtocolError} If required fields are missing
     */
    StudentProps.decode = function decode(reader, length) {
        if (!(reader instanceof $Reader))
            reader = $Reader.create(reader);
        let end = length === undefined ? reader.len : reader.pos + length, message = new $root.StudentProps();
        while (reader.pos < end) {
            let tag = reader.uint32();
            switch (tag >>> 3) {
            case 1: {
                    message.studentId = reader.string();
                    break;
                }
            default:
                reader.skipType(tag & 7);
                break;
            }
        }
        return message;
    };

    /**
     * Decodes a StudentProps message from the specified reader or buffer, length delimited.
     * @function decodeDelimited
     * @memberof StudentProps
     * @static
     * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
     * @returns {StudentProps} StudentProps
     * @throws {Error} If the payload is not a reader or valid buffer
     * @throws {$protobuf.util.ProtocolError} If required fields are missing
     */
    StudentProps.decodeDelimited = function decodeDelimited(reader) {
        if (!(reader instanceof $Reader))
            reader = new $Reader(reader);
        return this.decode(reader, reader.uint32());
    };

    /**
     * Verifies a StudentProps message.
     * @function verify
     * @memberof StudentProps
     * @static
     * @param {Object.<string,*>} message Plain object to verify
     * @returns {string|null} `null` if valid, otherwise the reason why it is not
     */
    StudentProps.verify = function verify(message) {
        if (typeof message !== "object" || message === null)
            return "object expected";
        if (message.studentId != null && message.hasOwnProperty("studentId"))
            if (!$util.isString(message.studentId))
                return "studentId: string expected";
        return null;
    };

    /**
     * Creates a StudentProps message from a plain object. Also converts values to their respective internal types.
     * @function fromObject
     * @memberof StudentProps
     * @static
     * @param {Object.<string,*>} object Plain object
     * @returns {StudentProps} StudentProps
     */
    StudentProps.fromObject = function fromObject(object) {
        if (object instanceof $root.StudentProps)
            return object;
        let message = new $root.StudentProps();
        if (object.studentId != null)
            message.studentId = String(object.studentId);
        return message;
    };

    /**
     * Creates a plain object from a StudentProps message. Also converts values to other types if specified.
     * @function toObject
     * @memberof StudentProps
     * @static
     * @param {StudentProps} message StudentProps
     * @param {$protobuf.IConversionOptions} [options] Conversion options
     * @returns {Object.<string,*>} Plain object
     */
    StudentProps.toObject = function toObject(message, options) {
        if (!options)
            options = {};
        let object = {};
        if (options.defaults)
            object.studentId = "";
        if (message.studentId != null && message.hasOwnProperty("studentId"))
            object.studentId = message.studentId;
        return object;
    };

    /**
     * Converts this StudentProps to JSON.
     * @function toJSON
     * @memberof StudentProps
     * @instance
     * @returns {Object.<string,*>} JSON object
     */
    StudentProps.prototype.toJSON = function toJSON() {
        return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
    };

    /**
     * Gets the default type url for StudentProps
     * @function getTypeUrl
     * @memberof StudentProps
     * @static
     * @param {string} [typeUrlPrefix] your custom typeUrlPrefix(default "type.googleapis.com")
     * @returns {string} The default type url
     */
    StudentProps.getTypeUrl = function getTypeUrl(typeUrlPrefix) {
        if (typeUrlPrefix === undefined) {
            typeUrlPrefix = "type.googleapis.com";
        }
        return typeUrlPrefix + "/StudentProps";
    };

    return StudentProps;
})();

export const Member = $root.Member = (() => {

    /**
     * Properties of a Member.
     * @exports IMember
     * @interface IMember
     * @property {string|null} [type] Member type
     * @property {string|null} [name] Member name
     * @property {ITeacherProps|null} [teacherProps] Member teacherProps
     * @property {IStudentProps|null} [studentProps] Member studentProps
     */

    /**
     * Constructs a new Member.
     * @exports Member
     * @classdesc Represents a Member.
     * @implements IMember
     * @constructor
     * @param {IMember=} [properties] Properties to set
     */
    function Member(properties) {
        if (properties)
            for (let keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                if (properties[keys[i]] != null)
                    this[keys[i]] = properties[keys[i]];
    }

    /**
     * Member type.
     * @member {string} type
     * @memberof Member
     * @instance
     */
    Member.prototype.type = "";

    /**
     * Member name.
     * @member {string} name
     * @memberof Member
     * @instance
     */
    Member.prototype.name = "";

    /**
     * Member teacherProps.
     * @member {ITeacherProps|null|undefined} teacherProps
     * @memberof Member
     * @instance
     */
    Member.prototype.teacherProps = null;

    /**
     * Member studentProps.
     * @member {IStudentProps|null|undefined} studentProps
     * @memberof Member
     * @instance
     */
    Member.prototype.studentProps = null;

    // OneOf field names bound to virtual getters and setters
    let $oneOfFields;

    /**
     * Member properties.
     * @member {"teacherProps"|"studentProps"|undefined} properties
     * @memberof Member
     * @instance
     */
    Object.defineProperty(Member.prototype, "properties", {
        get: $util.oneOfGetter($oneOfFields = ["teacherProps", "studentProps"]),
        set: $util.oneOfSetter($oneOfFields)
    });

    /**
     * Creates a new Member instance using the specified properties.
     * @function create
     * @memberof Member
     * @static
     * @param {IMember=} [properties] Properties to set
     * @returns {Member} Member instance
     */
    Member.create = function create(properties) {
        return new Member(properties);
    };

    /**
     * Encodes the specified Member message. Does not implicitly {@link Member.verify|verify} messages.
     * @function encode
     * @memberof Member
     * @static
     * @param {IMember} message Member message or plain object to encode
     * @param {$protobuf.Writer} [writer] Writer to encode to
     * @returns {$protobuf.Writer} Writer
     */
    Member.encode = function encode(message, writer) {
        if (!writer)
            writer = $Writer.create();
        if (message.type != null && Object.hasOwnProperty.call(message, "type"))
            writer.uint32(/* id 1, wireType 2 =*/10).string(message.type);
        if (message.name != null && Object.hasOwnProperty.call(message, "name"))
            writer.uint32(/* id 2, wireType 2 =*/18).string(message.name);
        if (message.teacherProps != null && Object.hasOwnProperty.call(message, "teacherProps"))
            $root.TeacherProps.encode(message.teacherProps, writer.uint32(/* id 3, wireType 2 =*/26).fork()).ldelim();
        if (message.studentProps != null && Object.hasOwnProperty.call(message, "studentProps"))
            $root.StudentProps.encode(message.studentProps, writer.uint32(/* id 4, wireType 2 =*/34).fork()).ldelim();
        return writer;
    };

    /**
     * Encodes the specified Member message, length delimited. Does not implicitly {@link Member.verify|verify} messages.
     * @function encodeDelimited
     * @memberof Member
     * @static
     * @param {IMember} message Member message or plain object to encode
     * @param {$protobuf.Writer} [writer] Writer to encode to
     * @returns {$protobuf.Writer} Writer
     */
    Member.encodeDelimited = function encodeDelimited(message, writer) {
        return this.encode(message, writer).ldelim();
    };

    /**
     * Decodes a Member message from the specified reader or buffer.
     * @function decode
     * @memberof Member
     * @static
     * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
     * @param {number} [length] Message length if known beforehand
     * @returns {Member} Member
     * @throws {Error} If the payload is not a reader or valid buffer
     * @throws {$protobuf.util.ProtocolError} If required fields are missing
     */
    Member.decode = function decode(reader, length) {
        if (!(reader instanceof $Reader))
            reader = $Reader.create(reader);
        let end = length === undefined ? reader.len : reader.pos + length, message = new $root.Member();
        while (reader.pos < end) {
            let tag = reader.uint32();
            switch (tag >>> 3) {
            case 1: {
                    message.type = reader.string();
                    break;
                }
            case 2: {
                    message.name = reader.string();
                    break;
                }
            case 3: {
                    message.teacherProps = $root.TeacherProps.decode(reader, reader.uint32());
                    break;
                }
            case 4: {
                    message.studentProps = $root.StudentProps.decode(reader, reader.uint32());
                    break;
                }
            default:
                reader.skipType(tag & 7);
                break;
            }
        }
        return message;
    };

    /**
     * Decodes a Member message from the specified reader or buffer, length delimited.
     * @function decodeDelimited
     * @memberof Member
     * @static
     * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
     * @returns {Member} Member
     * @throws {Error} If the payload is not a reader or valid buffer
     * @throws {$protobuf.util.ProtocolError} If required fields are missing
     */
    Member.decodeDelimited = function decodeDelimited(reader) {
        if (!(reader instanceof $Reader))
            reader = new $Reader(reader);
        return this.decode(reader, reader.uint32());
    };

    /**
     * Verifies a Member message.
     * @function verify
     * @memberof Member
     * @static
     * @param {Object.<string,*>} message Plain object to verify
     * @returns {string|null} `null` if valid, otherwise the reason why it is not
     */
    Member.verify = function verify(message) {
        if (typeof message !== "object" || message === null)
            return "object expected";
        let properties = {};
        if (message.type != null && message.hasOwnProperty("type"))
            if (!$util.isString(message.type))
                return "type: string expected";
        if (message.name != null && message.hasOwnProperty("name"))
            if (!$util.isString(message.name))
                return "name: string expected";
        if (message.teacherProps != null && message.hasOwnProperty("teacherProps")) {
            properties.properties = 1;
            {
                let error = $root.TeacherProps.verify(message.teacherProps);
                if (error)
                    return "teacherProps." + error;
            }
        }
        if (message.studentProps != null && message.hasOwnProperty("studentProps")) {
            if (properties.properties === 1)
                return "properties: multiple values";
            properties.properties = 1;
            {
                let error = $root.StudentProps.verify(message.studentProps);
                if (error)
                    return "studentProps." + error;
            }
        }
        return null;
    };

    /**
     * Creates a Member message from a plain object. Also converts values to their respective internal types.
     * @function fromObject
     * @memberof Member
     * @static
     * @param {Object.<string,*>} object Plain object
     * @returns {Member} Member
     */
    Member.fromObject = function fromObject(object) {
        if (object instanceof $root.Member)
            return object;
        let message = new $root.Member();
        if (object.type != null)
            message.type = String(object.type);
        if (object.name != null)
            message.name = String(object.name);
        if (object.teacherProps != null) {
            if (typeof object.teacherProps !== "object")
                throw TypeError(".Member.teacherProps: object expected");
            message.teacherProps = $root.TeacherProps.fromObject(object.teacherProps);
        }
        if (object.studentProps != null) {
            if (typeof object.studentProps !== "object")
                throw TypeError(".Member.studentProps: object expected");
            message.studentProps = $root.StudentProps.fromObject(object.studentProps);
        }
        return message;
    };

    /**
     * Creates a plain object from a Member message. Also converts values to other types if specified.
     * @function toObject
     * @memberof Member
     * @static
     * @param {Member} message Member
     * @param {$protobuf.IConversionOptions} [options] Conversion options
     * @returns {Object.<string,*>} Plain object
     */
    Member.toObject = function toObject(message, options) {
        if (!options)
            options = {};
        let object = {};
        if (options.defaults) {
            object.type = "";
            object.name = "";
        }
        if (message.type != null && message.hasOwnProperty("type"))
            object.type = message.type;
        if (message.name != null && message.hasOwnProperty("name"))
            object.name = message.name;
        if (message.teacherProps != null && message.hasOwnProperty("teacherProps")) {
            object.teacherProps = $root.TeacherProps.toObject(message.teacherProps, options);
            if (options.oneofs)
                object.properties = "teacherProps";
        }
        if (message.studentProps != null && message.hasOwnProperty("studentProps")) {
            object.studentProps = $root.StudentProps.toObject(message.studentProps, options);
            if (options.oneofs)
                object.properties = "studentProps";
        }
        return object;
    };

    /**
     * Converts this Member to JSON.
     * @function toJSON
     * @memberof Member
     * @instance
     * @returns {Object.<string,*>} JSON object
     */
    Member.prototype.toJSON = function toJSON() {
        return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
    };

    /**
     * Gets the default type url for Member
     * @function getTypeUrl
     * @memberof Member
     * @static
     * @param {string} [typeUrlPrefix] your custom typeUrlPrefix(default "type.googleapis.com")
     * @returns {string} The default type url
     */
    Member.getTypeUrl = function getTypeUrl(typeUrlPrefix) {
        if (typeUrlPrefix === undefined) {
            typeUrlPrefix = "type.googleapis.com";
        }
        return typeUrlPrefix + "/Member";
    };

    return Member;
})();

export const MemberInfo = $root.MemberInfo = (() => {

    /**
     * Properties of a MemberInfo.
     * @exports IMemberInfo
     * @interface IMemberInfo
     * @property {ICommonInfo|null} [commonInfo] MemberInfo commonInfo
     * @property {Array.<IMember>|null} [member] MemberInfo member
     */

    /**
     * Constructs a new MemberInfo.
     * @exports MemberInfo
     * @classdesc Represents a MemberInfo.
     * @implements IMemberInfo
     * @constructor
     * @param {IMemberInfo=} [properties] Properties to set
     */
    function MemberInfo(properties) {
        this.member = [];
        if (properties)
            for (let keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                if (properties[keys[i]] != null)
                    this[keys[i]] = properties[keys[i]];
    }

    /**
     * MemberInfo commonInfo.
     * @member {ICommonInfo|null|undefined} commonInfo
     * @memberof MemberInfo
     * @instance
     */
    MemberInfo.prototype.commonInfo = null;

    /**
     * MemberInfo member.
     * @member {Array.<IMember>} member
     * @memberof MemberInfo
     * @instance
     */
    MemberInfo.prototype.member = $util.emptyArray;

    /**
     * Creates a new MemberInfo instance using the specified properties.
     * @function create
     * @memberof MemberInfo
     * @static
     * @param {IMemberInfo=} [properties] Properties to set
     * @returns {MemberInfo} MemberInfo instance
     */
    MemberInfo.create = function create(properties) {
        return new MemberInfo(properties);
    };

    /**
     * Encodes the specified MemberInfo message. Does not implicitly {@link MemberInfo.verify|verify} messages.
     * @function encode
     * @memberof MemberInfo
     * @static
     * @param {IMemberInfo} message MemberInfo message or plain object to encode
     * @param {$protobuf.Writer} [writer] Writer to encode to
     * @returns {$protobuf.Writer} Writer
     */
    MemberInfo.encode = function encode(message, writer) {
        if (!writer)
            writer = $Writer.create();
        if (message.commonInfo != null && Object.hasOwnProperty.call(message, "commonInfo"))
            $root.CommonInfo.encode(message.commonInfo, writer.uint32(/* id 1, wireType 2 =*/10).fork()).ldelim();
        if (message.member != null && message.member.length)
            for (let i = 0; i < message.member.length; ++i)
                $root.Member.encode(message.member[i], writer.uint32(/* id 2, wireType 2 =*/18).fork()).ldelim();
        return writer;
    };

    /**
     * Encodes the specified MemberInfo message, length delimited. Does not implicitly {@link MemberInfo.verify|verify} messages.
     * @function encodeDelimited
     * @memberof MemberInfo
     * @static
     * @param {IMemberInfo} message MemberInfo message or plain object to encode
     * @param {$protobuf.Writer} [writer] Writer to encode to
     * @returns {$protobuf.Writer} Writer
     */
    MemberInfo.encodeDelimited = function encodeDelimited(message, writer) {
        return this.encode(message, writer).ldelim();
    };

    /**
     * Decodes a MemberInfo message from the specified reader or buffer.
     * @function decode
     * @memberof MemberInfo
     * @static
     * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
     * @param {number} [length] Message length if known beforehand
     * @returns {MemberInfo} MemberInfo
     * @throws {Error} If the payload is not a reader or valid buffer
     * @throws {$protobuf.util.ProtocolError} If required fields are missing
     */
    MemberInfo.decode = function decode(reader, length) {
        if (!(reader instanceof $Reader))
            reader = $Reader.create(reader);
        let end = length === undefined ? reader.len : reader.pos + length, message = new $root.MemberInfo();
        while (reader.pos < end) {
            let tag = reader.uint32();
            switch (tag >>> 3) {
            case 1: {
                    message.commonInfo = $root.CommonInfo.decode(reader, reader.uint32());
                    break;
                }
            case 2: {
                    if (!(message.member && message.member.length))
                        message.member = [];
                    message.member.push($root.Member.decode(reader, reader.uint32()));
                    break;
                }
            default:
                reader.skipType(tag & 7);
                break;
            }
        }
        return message;
    };

    /**
     * Decodes a MemberInfo message from the specified reader or buffer, length delimited.
     * @function decodeDelimited
     * @memberof MemberInfo
     * @static
     * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
     * @returns {MemberInfo} MemberInfo
     * @throws {Error} If the payload is not a reader or valid buffer
     * @throws {$protobuf.util.ProtocolError} If required fields are missing
     */
    MemberInfo.decodeDelimited = function decodeDelimited(reader) {
        if (!(reader instanceof $Reader))
            reader = new $Reader(reader);
        return this.decode(reader, reader.uint32());
    };

    /**
     * Verifies a MemberInfo message.
     * @function verify
     * @memberof MemberInfo
     * @static
     * @param {Object.<string,*>} message Plain object to verify
     * @returns {string|null} `null` if valid, otherwise the reason why it is not
     */
    MemberInfo.verify = function verify(message) {
        if (typeof message !== "object" || message === null)
            return "object expected";
        if (message.commonInfo != null && message.hasOwnProperty("commonInfo")) {
            let error = $root.CommonInfo.verify(message.commonInfo);
            if (error)
                return "commonInfo." + error;
        }
        if (message.member != null && message.hasOwnProperty("member")) {
            if (!Array.isArray(message.member))
                return "member: array expected";
            for (let i = 0; i < message.member.length; ++i) {
                let error = $root.Member.verify(message.member[i]);
                if (error)
                    return "member." + error;
            }
        }
        return null;
    };

    /**
     * Creates a MemberInfo message from a plain object. Also converts values to their respective internal types.
     * @function fromObject
     * @memberof MemberInfo
     * @static
     * @param {Object.<string,*>} object Plain object
     * @returns {MemberInfo} MemberInfo
     */
    MemberInfo.fromObject = function fromObject(object) {
        if (object instanceof $root.MemberInfo)
            return object;
        let message = new $root.MemberInfo();
        if (object.commonInfo != null) {
            if (typeof object.commonInfo !== "object")
                throw TypeError(".MemberInfo.commonInfo: object expected");
            message.commonInfo = $root.CommonInfo.fromObject(object.commonInfo);
        }
        if (object.member) {
            if (!Array.isArray(object.member))
                throw TypeError(".MemberInfo.member: array expected");
            message.member = [];
            for (let i = 0; i < object.member.length; ++i) {
                if (typeof object.member[i] !== "object")
                    throw TypeError(".MemberInfo.member: object expected");
                message.member[i] = $root.Member.fromObject(object.member[i]);
            }
        }
        return message;
    };

    /**
     * Creates a plain object from a MemberInfo message. Also converts values to other types if specified.
     * @function toObject
     * @memberof MemberInfo
     * @static
     * @param {MemberInfo} message MemberInfo
     * @param {$protobuf.IConversionOptions} [options] Conversion options
     * @returns {Object.<string,*>} Plain object
     */
    MemberInfo.toObject = function toObject(message, options) {
        if (!options)
            options = {};
        let object = {};
        if (options.arrays || options.defaults)
            object.member = [];
        if (options.defaults)
            object.commonInfo = null;
        if (message.commonInfo != null && message.hasOwnProperty("commonInfo"))
            object.commonInfo = $root.CommonInfo.toObject(message.commonInfo, options);
        if (message.member && message.member.length) {
            object.member = [];
            for (let j = 0; j < message.member.length; ++j)
                object.member[j] = $root.Member.toObject(message.member[j], options);
        }
        return object;
    };

    /**
     * Converts this MemberInfo to JSON.
     * @function toJSON
     * @memberof MemberInfo
     * @instance
     * @returns {Object.<string,*>} JSON object
     */
    MemberInfo.prototype.toJSON = function toJSON() {
        return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
    };

    /**
     * Gets the default type url for MemberInfo
     * @function getTypeUrl
     * @memberof MemberInfo
     * @static
     * @param {string} [typeUrlPrefix] your custom typeUrlPrefix(default "type.googleapis.com")
     * @returns {string} The default type url
     */
    MemberInfo.getTypeUrl = function getTypeUrl(typeUrlPrefix) {
        if (typeUrlPrefix === undefined) {
            typeUrlPrefix = "type.googleapis.com";
        }
        return typeUrlPrefix + "/MemberInfo";
    };

    return MemberInfo;
})();

export { $root as default };
