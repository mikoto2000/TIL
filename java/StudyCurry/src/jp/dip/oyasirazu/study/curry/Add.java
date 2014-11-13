package jp.dip.oyasirazu.study.curry;

/**
 * 型が「Integer -> Integer -> Integer」の足し算関数
 * 
 * @author mikoto
 * 
 */
public class Add implements IFunction<Integer> {

	private Integer x, y;

	public void setArgs(Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public Integer eval() {
		return x + y;
	}

}
