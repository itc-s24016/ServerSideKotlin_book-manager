package jp.ac.it_college.std.s24016.kotlin.book.manager.domain.model

//本のレンタル状況を表すデータクラス
data class BookWithRental(
    //BookクラスとRentalクラスの情報を保有、照合する
    val book: Book,
    val rental: Rental?, //レンタルされていない場合もあるので許容型
) {
    //このデータクラス特有のメソッド（レンタルされているか判断）
    val isRental: Boolean
        get() = rental != null //レンタルが Null でなければ True
}
