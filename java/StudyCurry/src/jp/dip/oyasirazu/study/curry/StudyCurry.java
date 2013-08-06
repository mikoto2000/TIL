package jp.dip.oyasirazu.study.curry;

public class StudyCurry {

	/**
	 * こんな感じで、 Add を CurryAdd1 と CurryAdd2 の組み合わせに変換することをカリー化というのかな？
	 */
	public static void main(String[] args) {
		Add add = new Add();
		add.setArgs(100, 120);
		System.out.println(add.eval());

		// Integer -> (Integer -> Integer) に対応
		ICurryFunction<Integer, ICurryFunction<Integer, Integer>> cAdd1 = new CurryAdd1();

		cAdd1.setArg(100);

		// (Integer -> Integer) に対応
		ICurryFunction<Integer, Integer> cAdd2 = cAdd1.eval();

		cAdd2.setArg(120);
		System.out.println(cAdd2.eval());

		// 実はこの cAdd2 は、 x == 100 として部分適用された Add 関数である。
		cAdd2.setArg(190);
		System.out.println(cAdd2.eval());

		add.setArgs(100, 190);
		System.out.println(add.eval());

		// カリー化した関数は、「先頭の引数を部分適用した関数を返す関数」という事になるのか？
	}

}
