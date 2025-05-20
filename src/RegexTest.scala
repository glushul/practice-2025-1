import collection.mutable.Stack
import org.scalatest._

class ExampleSpec extends WordSpec with Matchers {
  "Оценщик регулярных выражений" should {
    "сопоставляет эти основные регулярные выражения" in {
      Regex.fullMatch("ab", "ab") should be(true)
      Regex.fullMatch("abbbbb", "ab+") should be(true)
      Regex.fullMatch("bbbbb", "ab+") should be(false)

      Regex.fullMatch("aaabbb", "a+b+") should be(true)
      Regex.fullMatch("ababa", "a+b+") should be(false)
      
      Regex.fullMatch("aaa", "a*") should be(true)
      Regex.fullMatch("", "a*") should be(true) 
    }

    "сопоставляет символ . правильно" in {
        full("a", ".") should be (true)
        full("ab", ".") should be(false)
        full("abab", "(..)*") should be(true)
        full("aba", "(..)*") should be (false)
    }

    "сопоставляет более сложные регулярные выражения" in {
      Regex.fullMatch("a", "(a*)*") should be (true)
      Regex.fullMatch("b", "(a*)*") should be (false)  
      Regex.fullMatch("abc", "abc") should be (true) 
   
      Regex.fullMatch("abc", "(a|b)bc") should be (true)  
      Regex.fullMatch("abc", "(a|b)+bc") should be (true)  
      Regex.fullMatch("abc", "(a|b)+b*c") should be (true)  
      Regex.fullMatch("abc", "((a|b)+b*c)+") should be (true)  
    }

    "сопоставляет правильно 10000 раз" in {
      (1 to 10000).foreach(_ => full("abcabcabcabcabcabcabcd", "((a|b)+b*c)+"))
    }

    def full(input: String, pattern: String) = Regex.fullMatch(input, pattern)
  }
}