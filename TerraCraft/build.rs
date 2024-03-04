fn main() {
    #[cfg(debug_assertions)]
    const CONFIGURATION: &'static str = "Debug";

    #[cfg(not(debug_assertions))]
    const CONFIGURATION: &'static str = "Release";

    // check if compiling for windows or linux
    #[cfg(target_os = "windows")]
    const NET_RUNTIME: &'static str = "win-x64";

    #[cfg(target_os = "linux")]
    const NET_RUNTIME: &'static str = "linux-x64";

    // println!(
    //     "cargo:rustc-link-arg=-Wl,-rpath,../TerraCraftNET/bin/{}/net8.0/linux-x64/publish",
    //     CONFIGURATION
    // );
    println!("cargo:rustc-link-lib=dylib=terracraftnet");
    // println!(
    //     "cargo:rustc-link-search=native=../TerraCraftNET/bin/{}/net8.0/{}/native",
    //     CONFIGURATION, NET_RUNTIME
    // );
    println!(
        "cargo:rustc-link-search=native=../TerraCraftNET/bin/Release/net8.0/{}/native",
        NET_RUNTIME
    );
}
