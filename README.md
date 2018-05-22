# SimpleOkhttpResponseTimeAnalyst

Simple Okhttp Response Time Analyst (SORTA) is a tool to collect all OkHttpLoggingIntercepter info that response time is over than we expect.
You can use it when you are testing your app with API. 


###Download
<pre>git clone https://github.com/rockifanze/SimpleOkhttpResponseTimeAnalyst.git</pre>

### Build
<pre>
./gradlew build
</pre>
### Usage
Move all logcat info to the current path, and enter the command below.
<pre>
cd build/libs
java -jar sorta.jar .
</pre>
Then will create "warnings.txt" at the "currentPath/warningDocs/"

### Options

- Specify the response time threshold
Input sample
<pre>
# ./input.txt
# 05-18 03:05:16.290: D/OkHttp(9103): <-- 200  https://my.api.com/profile/me (834ms)
# 05-18 03:05:17.249: D/OkHttp(9103): <-- 200  https://my.api.com/comment/list?post_id=670 (218ms)
</pre>
enter command below
<pre>
java -jar sorta.jar -t 500
</pre>
The warning result will include the first line, but exclude the second.
> If you want to know the result when executing command, add "-verbose" option.

- Make all result in one file by using "-onefile". 
<pre>
java -jar sorta.jar -onefile
</pre>

- Specify the output path by using "-output"
<pre>
java -jar sorta.jar -output ./outputPath
</pre>

