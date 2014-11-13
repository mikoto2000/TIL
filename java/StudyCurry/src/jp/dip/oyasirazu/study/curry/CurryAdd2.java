package jp.dip.oyasirazu.study.curry;

/**
 * 「Integer -> Integer -> Integer」 を
 * 「Integer -> (Integer -> Integer)」に変換したもののうち、
 * 「(Integer -> Integer)」を表す部分の関数。
 * 
 * @author mikoto
 * 
 */
public class CurryAdd2 implements ICurryFunction<Integer, Integer> {

	private Integer x, y;

	public CurryAdd2(Integer x) {
		this.x = x;
	}

	@Override
	public void setArg(Integer y) {
		this.y = y;
	}

	@Override
	public Integer eval() {
		return x + y;
	}
}
