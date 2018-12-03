package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }
//
//  /**
//   * Exercise 1
//   */
  def pascal(c: Int, r: Int): Int = {
    val lastColumn = r
    if (c == 0 || r == 0 || c == lastColumn) return 1
    pascal(c - 1, r - 1) + pascal(c, r - 1)
  }
//
//  /**
//   * Exercise 2
//   */
  def balance(chars: List[Char]): Boolean = {
    def readBracketStack(bracketStack: List[Char], remainingChars: List[Char]):List[Char] = {
      if (remainingChars.isEmpty) return bracketStack
      if (remainingChars.head == '(') return readBracketStack(bracketStack :+ '(', remainingChars.tail)

      if (remainingChars.head == ')') {
        if (bracketStack.isEmpty) return List[Char]('c')
        return readBracketStack(bracketStack.dropRight(1), remainingChars.tail)
      }

      readBracketStack(bracketStack, remainingChars.tail)
    }

    readBracketStack(List[Char](), chars).isEmpty
  }

//  /**
//   * Exercise 3
//   */

  def countChange(money: Int, coins: List[Int]): Int = {
    var allUsedCoins = List[String]()

    def countChangeWithCoin(money: Int, allCoins: List[Int], usedCoins: String): Int = {
      if (money == 0) {
        if (isUsedCoinsExisted(usedCoins)) return 0
        allUsedCoins = getCombinedUsedCoins(usedCoins)
        return 1
      }

      val usableCoins: List[Int] = coinsLessThanMoney(money, allCoins)
      if (usableCoins.isEmpty) return 0

      println((usableCoins))
      usableCoins.reduce((coinA, coinB) => {
//        debug here, coinA is empty String?
        countChangeWithCoin(money - coinA, allCoins, coinsToID(usedCoins, coinA)) +
          countChangeWithCoin(money - coinB, allCoins, coinsToID(usedCoins, coinB))
      })
    }

    def isUsedCoinsExisted(usedCoinID: String): Boolean = {
      allUsedCoins.contains(usedCoinID)
    }

    def getCombinedUsedCoins(usedCoinID: String): List[String] = {
      allUsedCoins :+ usedCoinID
    }

    def coinsToID(usedCoins: String, newCoin: Int): String = {
      val usedCoinsList = usedCoins.split(',')
      if (usedCoinsList.isEmpty) return newCoin.toString

      usedCoinsList
        .map(usedCoin => usedCoin.toInt)
        .+:(newCoin)
        .sortBy(coin => coin)
        .mkString(",")
    }


    def coinsLessThanMoney(money: Int, allCoins: List[Int]): List[Int] = {
      allCoins.filter(coin => coin < money)
    }
    countChangeWithCoin(money, coins, "")
  }
}
