$fileDir = Split-Path -Parent $MyInvocation.MyCommand.Path
cd $fileDir
java ${talend.job.jvmargs} -cp ${talend.job.bat.classpath} ${talend.job.class} ${talend.job.bat.addition} %* 