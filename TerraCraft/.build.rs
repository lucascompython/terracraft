fn main() {
    #[cfg(debug_assertions)]
    const CONFIGURATION: &'static str = "Debug";

    #[cfg(not(debug_assertions))]
    const CONFIGURATION: &'static str = "Release";

    println!(
        "cargo:rustc-link-search=native=../../TerraCraftNET/bin/{}/net7.0",
        CONFIGURATION
    );
    println!("cargo:rustc-link-lib=static=TerraCraftNET");
}
