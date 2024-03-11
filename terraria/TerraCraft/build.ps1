#!/usr/bin/env pwsh

$os = ""
if ($env:OS -eq "Windows_NT") {
    $os = "win"
}
elseif ($IsLinux) {
    $os = "linux"
}
else {
    Write-Host "Unsupported OS"
    exit 1
}

dotnet publish -c Release -r $os-x64 --self-contained true # -p:PublishTrimmed=true 

# move 
# Google.Protobuf.dll
# Grpc.Core.Api.dll
# Grpc.Net.Client.dll
# Grpc.Net.Common.dll
# Microsoft.Extensions.Logging.Abstractions.dll
# TerraCraft.dll
# to a new folder

New-Item -ItemType Directory -Force -Path build 

$path = "TerraCraft/bin/Release/net6.0/$os-x64/publish"

Move-Item -Path "$path/Grpc.Core.Api.dll", "$path/Grpc.Net.Client.dll", "$path/Grpc.Net.Common.dll", "$path/Microsoft.Extensions.Logging.Abstractions.dll", "$path/TerraCraft.dll" -Destination "build" -Force