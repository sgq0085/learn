namespace java com.gqshao.thirft.service
service HelloWorldService{   
    string helloString(1:string param)   
    i32 helloInt(1:i32 param)   
    bool helloBoolean(1:bool param)   
    void helloVoid()   
    string helloNull()   
    i32 add(1:i32 n1, 2:i32 n2)
}