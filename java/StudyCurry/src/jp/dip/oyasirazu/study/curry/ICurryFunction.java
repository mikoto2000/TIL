package jp.dip.oyasirazu.study.curry;

/**
 * カリー関数
 * 
 * @author mikoto
 * 
 * @param <AT>
 *            関数に渡される引数
 * @param <RT>
 *            関数の結果
 */
public interface ICurryFunction<AT, RT> extends IFunction<RT> {
	void setArg(AT arg);
}
