package jp.dip.oyasirazu.study.lombok;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Main
 */
public class Main {
    public static void main(String[] args) {
        final Date date = new Date();

        // デフォルトコンストラクタを使用
        TestDataClass tdc1 = new TestDataClass();
        tdc1.setStringData("TESTING1");
        tdc1.setIntData(100);
        tdc1.setDateData(date);
        System.out.println(tdc1);

        // 引数付きコンストラクタを使用
        TestDataClass tdc2 = new TestDataClass(
                "TESTING2",
                200,
                date);
        System.out.println(tdc2);
    }

    // Lombok に任せて作るオブジェクト
    // Setter/Getter, equals, hashCode が自動生成されるはず。
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    static class TestDataClass {
        private String stringData;
        private int intData;
        private Date dateData;
    }
}
