package com.infrastructure.concurrent.synchronize.jol;

import com.infrastructure.concurrent.synchronize.EmptyObject;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 *  java object layout
 *  jdk8 默认开启指针压缩
 *
 * 关闭指针压缩： -XX:-UseCompressedOops
 * 开启指针压缩： -XX:+UseCompressedOops
 *
 *JVM的实现方式是，不再保存所有引用，而是每隔8个字节保存一个引用。例如，原来保存每个引用0、1、2...，现在只保存0、8、16...。
 * 因此，指针压缩后，并不是所有引用都保存在堆中，而是以8个字节为间隔保存引用。
 * 在实现上，堆中的引用其实还是按照0x0、0x1、0x2...进行存储。只不过当引用被存入64位的寄存器时，
 * JVM将其左移3位（相当于末尾添加3个0），例如0x0、0x1、0x2...分别被转换为0x0、0x8、0x10。而当从寄存器读出时，
 * JVM又可以右移3位，丢弃末尾的0。
 * （oop在堆中是32位，在寄存器中是35位，2的35次方=32G。也就是说，使用32位，来达到35位oop所能引用的堆内存空间）
 *
 *
 */
public class JOL01 {
    public static void main(String[] args) {
        EmptyObject emptyObject = new EmptyObject();
        //System.out.println(Integer.toHexString(emptyObject.hashCode()));
        System.out.println(ClassLayout.parseInstance(emptyObject).toPrintable());
        System.out.println(VM.current().details());
    }
}
