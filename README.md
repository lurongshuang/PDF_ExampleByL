<div>
<img src="https://github.com/lurongshuang/FBReader-Mupdf_library/blob/master/image/1.png" width="200" alt="PDF阅读" />
<img src="https://github.com/lurongshuang/FBReader-Mupdf_library/blob/master/image/8.png" width="200" alt="目录" />
</div>
由于工作需要, 阅读PDF文件，但是之前写的FBReader+MUPDF的ARR太大了，现在将查看PDF功能拆分出来。
就是这么个功能。


1.使用的时候 记得申请 网络，文件相关的权限
2.并且 将 activity 声明如下：
```
  <!--pdf-->
  <activity android:name="com.artifex.mupdfdemo.MarkLineActivity" >
  </activity>
  <activity
      android:name="com.artifex.mupdfdemo.bookmark.BookMark"
      android:theme="@style/Theme.AppCompat.NoActionBar" >
  </activity>
  <activity
      android:name="com.artifex.mupdfdemo.MuPDFActivity"
      android:theme="@android:style/Theme.Light.NoTitleBar.Fullscreen">
  </activity>
  <activity
      android:name="com.artifex.mupdfdemo.OutlineActivity"
      android:label="@string/outline_title"
      android:theme="@style/Theme.AppCompat.NoActionBar" >
  </activity>
  <activity
      android:name="com.artifex.mupdfdemo.PrintDialogActivity"
      android:label="@string/print"
      android:theme="@style/Theme.AppCompat.NoActionBar" >
  </activity>
  <activity android:name="com.artifex.mupdfdemo.NetWorkPDF.NetDowActivity"
      android:theme="@style/Theme.AppCompat.Dialog"
      ></activity>
  <!--pdf-->
  ```
