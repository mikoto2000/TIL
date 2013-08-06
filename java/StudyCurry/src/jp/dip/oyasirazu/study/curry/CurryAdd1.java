package jp.dip.oyasirazu.study.curry;

/**
 * 足し算関数のカリー化関数「Integer -> (Integer -> Integer)」　
 * 
 * @author mikoto
 * 
 */
public class CurryAdd1 implements
		ICurryFunction<Integer, ICurryFunction<Integer, Integer>> {

	private Integer x;

	@Override
	public void setArg(Integer x) {
		this.x = x;
	}

	@Override
	public ICurryFunction<Integer, Integer> eval() {
		return new CurryAdd2(x);
	}
}
