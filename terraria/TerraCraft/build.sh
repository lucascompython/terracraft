#!/usr/bin/env bash

dotnet publish -c Release -r "linux-x64" --self-contained true # -p:PublishTrimmed=true 

mkdir -p build

path="TerraCraft/bin/Release/net6.0/linux-x64/publish"

mv "$path/Grpc.Core.Api.dll" "$path/Grpc.Net.Client.dll" "$path/Grpc.Net.Common.dll" "$path/Microsoft.Extensions.Logging.Abstractions.dll" "$path/TerraCraft.dll" build/