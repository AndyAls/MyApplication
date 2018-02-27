package cn.evun.swiperecyclerview;


import java.io.Serializable;

/**
 * Created by Shuai.Li13 on 2017/7/10.
 */

public class Peason  implements Serializable{

    public String name;
    public String number;

    public Peason(String name, String number) {
        this.name = name;
        this.number = number;
    }
}
