package com.example.pkg

import com.example.pkg

object Main {
  def main(args: Array[String]): Unit = {

    var mySql = new MySql
    mySql.delete()

  }

  //  implicit class DB(mySql: MySql) {
  //    private val name = "Oracle"
  //    def delete() = {
  //      println("Deleting ... " + name + ", " + mySql)
  //    }
  //  }

  implicit def convert(mySql: MySql): DB = {
    new DB
  }

  implicit var greeting: String = "Hello"

  def sayHi(implicit greet: String = "Aloha") = {
    println(greet + " Tom")
  }

}

class MySql {
  def insert() = {
    println("Inserting ...")
  }
}


class DB {
  private val name = "Oracle"

  def delete() = {
    println("Deleting ... " + name)
  }
}
