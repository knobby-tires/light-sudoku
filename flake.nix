{
  description = "Light Sudoku - Android development environment";

  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
    android-nixpkgs = {
      url = "github:tadfisher/android-nixpkgs";
      inputs.nixpkgs.follows = "nixpkgs";
    };
  };

  outputs = { self, nixpkgs, android-nixpkgs }:
    let
      system = "x86_64-linux";
      pkgs = import nixpkgs {
        inherit system;
        config.allowUnfree = true;
        config.android_sdk.accept_license = true;
      };

      androidSdk = android-nixpkgs.sdk.${system} (sdkPkgs: with sdkPkgs; [
        build-tools-34-0-0
        cmdline-tools-latest
        platform-tools
        platforms-android-34
      ]);

      fhs = pkgs.buildFHSEnv {
        name = "gradle-env";
        targetPkgs = pkgs: with pkgs; [
          androidSdk
          gradle
          jdk17
          zlib
          ncurses5
          stdenv.cc.cc.lib
        ];
        runScript = "bash";
        profile = ''
          export ANDROID_HOME="${androidSdk}/share/android-sdk"
          export ANDROID_SDK_ROOT="${androidSdk}/share/android-sdk"
          export JAVA_HOME="${pkgs.jdk17}"
          export GRADLE_USER_HOME="$HOME/.gradle-fhs"
        '';
      };

    in {
      devShells.${system}.default = pkgs.mkShell {
        buildInputs = [
          androidSdk
          pkgs.jdk17
          pkgs.gradle
          fhs
        ];

        ANDROID_HOME = "${androidSdk}/share/android-sdk";
        ANDROID_SDK_ROOT = "${androidSdk}/share/android-sdk";
        JAVA_HOME = "${pkgs.jdk17}";

        shellHook = ''
          echo "dev environment loaded"
        '';
      };
    };
}
