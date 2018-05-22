# SimpleOkhttpResponseTimeAnalyst

Download
<pre>git clone https://github.com/rockifanze/SimpleOkhttpResponseTimeAnalyst.git</pre>

### Build
<pre>
./gradlew build
</pre>
### Usage
<pre>
cd build/libs
java -jar sorta.jar ./
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

