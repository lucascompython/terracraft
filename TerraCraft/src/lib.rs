use jni::{objects::JClass, sys::jint, JNIEnv};

mod jvm;
mod net;

#[no_mangle]
pub extern "system" fn Java_terracraft_TerraCraft_addRust<'local>(
    mut env: JNIEnv<'local>,
    class: JClass<'local>,
    a: jint,
    b: jint,
) -> jint {
    unsafe { net::Print("From Rust".as_ptr()) }
    println!("Rust called from Java!");
    a + b
}

#[no_mangle]
pub extern "C" fn mult_rust(a: i32, b: i32) -> i32 {
    println!("Rust called from C#!");
    a + b
}
