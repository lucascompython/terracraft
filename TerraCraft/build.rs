fn main() {
    #[cfg(debug_assertions)]
    const CONFIGURATION: &'static str = "Debug";

    #[cfg(not(debug_assertions))]
    const CONFIGURATION: &'static str = "Release";

    println!("cargo:rustc-link-arg=-Wl,-rpath,/home/lucas/Desktop/terracraft/TerraCraftNET/bin/{}/net7.0/linux-x64/publish", CONFIGURATION);
    println!("cargo:rustc-link-lib=dylib=terracraftnet");
    println!("cargo:rustc-link-search=native=/home/lucas/Desktop/terracraft/TerraCraftNET/bin/{}/net7.0/linux-x64/publish", CONFIGURATION);
}
