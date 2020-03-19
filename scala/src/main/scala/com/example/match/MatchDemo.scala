package com.example.matchs

/**
 * @author yulshi
 * @create 2020/02/21 15:56
 */
object MatchDemo {

  // 计算捆绑销售的产品价格

  def main(args: Array[String]): Unit = {

    var bundle = Bundle("books", 10,
      Book("Comics", 40),
      Bundle("IT Books", 20,
        Book("Java", 80),
        Book("Scala", 40)))

    val res = calculatePrice(bundle)
    println(res)

  }

  def calculatePrice(item: Item): Double = {
    item match {
      case Book(_, price) => price
      case Bundle(_, discount, items@_*) => {
        items.map(calculatePrice _).sum - discount
      }
    }
  }

  abstract class Item

  case class Book(desc: String, price: Double) extends Item

  case class Bundle(desc: String, discount: Double, item: Item*) extends Item {
    //    def getFinalPrice: Double = {
    //      var finalPrice = 0.0
    //      for(i <- item) {
    //        if(i.isInstanceOf[Bundle]) {
    //          finalPrice += i.asInstanceOf[Bundle].getFinalPrice
    //        } else if(i.isInstanceOf[Book]) {
    //          finalPrice += i.asInstanceOf[Book].price
    //        }
    //      }
    //      finalPrice - discount
    //    }
  }

}

