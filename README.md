# asyncGET

## ABOUT

This program is quite bare, it is meant to be extended. I am really just using it as an excuse to 
write some Java and learn how to use git and github. It's purpose is to be a general asynchronous http
utility.

I have only implemented one mode as of yet. 
Give it a text file with urls separated by newlines and it will download all of them asynchronously.

More modes are possible by creating different UrlFileParsers and AsyncRequestHandlers.

## BUILDING

You need ant [http://ant.apache.org/] to build. CD to the asyncGET directory then just run the ant command.
This will create a 'bin' and a 'dist' folder. 

## RUNNING

Here is the usage statement:
>
>	asyncGET <urls_file> <output_directory> <num_simultaneous_connections>
>
Here is an example command:
>
>	java -jar dist/asyncGET.jar test/resources/test-urls-small.txt myoutputdirectory 30
>

 


