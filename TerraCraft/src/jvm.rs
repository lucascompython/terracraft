use jni::JNIEnv;

use jni::objects::{JClass, JValue, JValueGen};
use jni::sys::{jint, jstring};

fn java_string(env: &JNIEnv, string: &str) -> jstring {
    **env
        .new_string(string)
        .expect("Couldn't create java string!")
}

// make it easy to transfor &str into jstring

trait JavaStringExt {
    fn to_java_string(self, env: &JNIEnv) -> jstring;
}

impl JavaStringExt for &str {
    fn to_java_string(self, env: &JNIEnv) -> jstring {
        java_string(env, self)
    }
}

//extern "C" {
//fn MultiplyNumbers(a: jint, b: jint) -> jint;
//}

#[no_mangle]
pub extern "system" fn Java_terracraft_TerraCraft_setup<'local>(
    mut env: JNIEnv<'local>,

    class: JClass<'local>,
) -> jstring {
    //let input: String = env
    //.get_string(&input)
    //.expect("Couldn't get java string!")
    //.into();

    //let output = env
    //.new_string(format!("Hello, {}!", input))
    //.expect("Couldn't create java string!");

    let result: Result<JValueGen<jni::objects::JObject<'_>>, jni::errors::Error> =
        env.call_static_method(class, "add", "(II)I", &[JValue::Int(69), JValue::Int(420)]);

    match result {
        Ok(v) => {
            println!("Call static method success: {:?}", v);
            if let JValueGen::Int(v) = v {
                println!("Java returned: {}!", v);
            } else {
                println!("Java returned: something else...");
            }

            //let value: i32 = v.i().unwrap();
            //println!("Java returned: {}!", value)
        }
        Err(e) => println!("Call static method error: {:?}", e),
    }

    //let net_result = unsafe { MultiplyNumbers(69, 420) };
    //println!("NET returned: {}!", net_result);

    "Error from RUST".to_java_string(&env)
}
