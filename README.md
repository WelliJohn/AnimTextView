# AnimTextView
## 1.效果图
![](https://user-gold-cdn.xitu.io/2018/1/3/160bb54c66973fb9?w=282&h=500&f=gif&s=372383)
## 2.定制的属性
* textColor 字体颜色
* textSize  字体大小
* duration  文字显示出来的时间


## 3.使用说明
implementation 'wellijohn.org.animtv:animtv:1.0.1'
```
<wellijohn.org.animtv.AnimTextView
        android:id="@+id/atv"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:padding="10dp"
        app:textColor="@color/colorAccent" 
        app:textSize="20sp" 
        app:duration="5000"/>
```
使用的时候，直接在animTv.setText(34.2);就可以了，在这里需要注意的是，这里的数值只支持整型和小数显示，小数只支持到小数点后两个位，如果有小数点后有3位以上，自动四舍五入



