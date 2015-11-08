package jp.dip.oyasirazu.study.jruby;

import org.jruby.Ruby;
import org.jruby.runtime.builtin.IRubyObject;

/**
 * StudyJRuby01
 */
public class StudyJRuby01 {
    /**
     * Constructor
     */
    public StudyJRuby01() {

    }

    public static void main(String[] args) {
        Ruby ruby = Ruby.newInstance();
        IRubyObject result = ruby.evalScriptlet("'Hello, World!'");
        System.out.println(result);
    }
}
