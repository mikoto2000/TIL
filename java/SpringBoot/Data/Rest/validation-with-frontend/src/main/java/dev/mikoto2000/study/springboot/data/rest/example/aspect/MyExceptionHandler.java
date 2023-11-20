package dev.mikoto2000.study.springboot.data.rest.example.aspect;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@AllArgsConstructor
@RestControllerAdvice
public class MyExceptionHandler {

    private MessageSource messageSource;

    /**
     * エンティティのバリデーションに失敗したときのエラーレスポンス。
     * 
     * @param errors エラーを表す例外インスタンス
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException errors) {
        var validationErrors = errors.getConstraintViolations()
                .stream()
                .map(violation -> new ValidationError(violation.getPropertyPath().toString(), violation.getMessage()))
                .toList();

        return new ErrorResponse(validationErrors);
    }

    /**
     * JSON のパースに失敗したときのエラーレスポンス。
     * 
     * @param e       エラーを表す例外インスタンス
     * @param request エラーが発生した原因の HttpServletRequest
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(HttpMessageNotReadableException e,
            HttpServletRequest request) {

        List<ValidationError> errors = Arrays
                .asList(new ValidationError(request.getRequestURI(), e.getLocalizedMessage()));

        return new ErrorResponse(errors);
    }

    /**
     * 一意制約違反のエラーレスポンス。
     *
     * @param e エラーを表す例外インスタンス
     * @return
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDataIntegrityViolationException(DataIntegrityViolationException e) {

        var uniqueErrorInfo = extractUniqueErrorInfo(e.getMessage());
        var errorMessage = messageSource.getMessage("error.unique",
                new Object[] { uniqueErrorInfo.value }, Locale.getDefault());
        List<ValidationError> errors = Arrays
                .asList(new ValidationError(uniqueErrorInfo.columnName,
                        errorMessage));

        return new ErrorResponse(errors);
    }

    /**
     * 列名と値のセットを管理するレコード
     */
    private record UniqueErrorInfo(String columnName, String value) {
    }

    /**
     * PostgreSQL の一意制約違反メッセージから、列名と値を抽出する。
     *
     * @param message 一意制約違反メッセージ
     * @return 列名と値のセット
     */
    private static UniqueErrorInfo extractUniqueErrorInfo(String message) {
        // 正規表現パターンの定義
        String patternString = "Key \\((.*?)\\)=\\((.*?)\\)";
        Pattern pattern = Pattern.compile(patternString);

        // マッチャーの作成とマッチングの実行
        Matcher matcher = pattern.matcher(message);

        if (matcher.find()) {
            // グループ1は列名、グループ2は値
            String columnName = matcher.group(1);
            String value = matcher.group(2);

            return new UniqueErrorInfo(columnName, value);
        } else {
            throw new RuntimeException("一意制約違反のメッセージフォーマットが想定するものと違います");
        }
    }
}
