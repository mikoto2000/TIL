package jp.dip.oyasirazu.study.curry;

/**
 * 
 * (カリーでない)関数
 * 
 * @author mikoto
 * 
 * @param <RT>
 *            関数の戻り値
 */
public interface IFunction<RT> {
	RT eval();
}
