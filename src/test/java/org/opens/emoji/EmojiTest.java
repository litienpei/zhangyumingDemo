package org.opens.emoji;

import com.github.binarywang.java.emoji.EmojiConverter;
import org.junit.Test;

public class EmojiTest {

    private EmojiConverter emojiConverter = EmojiConverter.getInstance();

    /**
     * 简介:
     *      将emoji表情转换为常规字符串
     */
    @Test
    public void test1() {
        String str = "  An 😃😀awesome 😃😃string with a few 😃😉emojis!";
        String alias = this.emojiConverter.toAlias(str);
        System.out.println(str);
        System.out.println("EmojiConverterTest.testToAlias()=====>");
        System.out.println(alias);
        System.out.println(":no_good: :ok_woman: :couple_with_heart:An :smiley::grinning:awesome :smiley::smiley:string with a few :smiley::wink:emojis!".equals(alias));
    }

    /**
     * 简介:
     *      可以直接将toHtml转换后的字符串保存到数据库，显示的时候，就直接显示，不需要再转义，HTML是支持的
     * 用途:
     *      这种方法的最大好处就是数据库的编码还是utf-8，而不必改成utf8mb4
     */
    @Test
    public void test2() {
        String str = "  An 😀😃awesome 😃😃string with a few 😉😃emojis!";
        String result = this.emojiConverter.toHtml(str);
        System.out.println(str);
        System.out.println("EmojiConverterTest.testToHtml()=====>");
        System.out.println(result);
        System.out.println("&#128581; &#128582; &#128145;An &#128512;&#128515;awesome &#128515;&#128515;string with a few &#128521;&#128515;emojis!".equals(result));
    }

}
