package com.example.kotlintraining

class Training {

    /*
    Необходимо создать интерфейс Publication, у которого должно быть свойства – price и wordCount,
    а также метод getType, возвращающий строку. Создать два класса, реализующих данный интерфейс –
    Book и Magazine. В методе getType для класса Book возвращаем строку с зависимости от количества
    слов. Если количество слов не превышает 1000, то вывести “Flash Fiction”, 7,500 –“Short Story”,
    всё, что выше – “Novel”. Для класса Magazine возвращаем строку “Magazine”.
    */
    interface Publication {
        val price: Double
        val wordCount: Int

        fun getType(): String
    }

    class Book(override val price: Double, override val wordCount: Int) : Publication {
        override fun getType(): String {
            return when {
                wordCount <= 1000 -> "Flash Fiction"
                wordCount <= 7500 -> "Short Story"
                else -> "Novel"
            }
        }

        override fun equals(other: Any?): Boolean {
            if (other is Book && (other.price == price || other.wordCount == wordCount))
                return true
            return false
        }

        override fun hashCode(): Int {
            var result = price.hashCode()
            result = 31 * result + wordCount
            return result
        }
    }

    class Magazine(override val price: Double, override val wordCount: Int) : Publication {
        override fun getType(): String {
            return "Magazine"
        }
    }

    /*
    Создать два объекта класса Book и один объект Magazine. Вывести в лог для каждого объекта тип,
    количество строк и цену в евро в отформатированном виде. У класса Book переопределить метод
    equals и произвести сравнение сначала по ссылке, затем используя метод equals. Результаты
    сравнений вывести в лог.
     */
    fun testTask() {
        val book1 = Book(33.0, 10000)
        val book2 = Book(33.0, 10000)
        val magazine = Magazine(342.43, 2000)

        printInfo(book1)
        printInfo(book2)
        printInfo(magazine)

        println("Равенство по ссылке - " + (book1 === book2))
        println("Равенство по содержанию - " + (book1 == book2))
    }

    private fun printInfo(printable: Publication) {
        println(
            "type - " + printable.getType() +
                " words count - " + printable.wordCount +
                " price - €" + printable.price
        )
    }

    /*
    Создать метод buy, который в качестве параметра принимает Publication (notnull - значения) и
    выводит в лог “The purchase is complete. The purchase amount was [цена издания]”. Создать две
    переменных класса Book, в которых могут находиться null значения. Присвоить одной null, а второй
    любое notnull значение. Используя функцию let, попробуйте вызвать метод buy с каждой из
    переменных.
    */

    fun thirdTask() {
        val book: Book? = null
        val book1: Book? = Book(33.23, 54)

        book?.let { buy(book) }
        book1?.let { buy(book1) }
    }

    private fun buy(publication: Publication) {
        println("The purchase is complete. The purchase amount was " + publication.price)
    }

    /*
    Создать переменную sum и присвоить ей лямбда-выражение, которое будет складывать два переданных
    ей числа и выводить результат в лог. Вызвать данное лямбда-выражение с произвольными
    параметрами.
     */
    val sum = { x: Int, y: Int -> println(x + y) }

    fun fourthTask() {
        sum(1, 8)
    }
}
