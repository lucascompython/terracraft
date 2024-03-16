dotnet publish -c Release -r win-x64 --self-contained true # -p:PublishTrimmed=true 

if ($LASTEXITCODE -ne 0) {
    exit $LASTEXITCODE
}

New-Item -ItemType Directory -Force -Path build 

$path = "TerraCraft/bin/Release/net6.0/win-x64/publish"

Move-Item -Path "$path/Grpc.Core.Api.dll", "$path/Grpc.Net.Client.dll", "$path/Grpc.Net.Common.dll", "$path/Microsoft.Extensions.Logging.Abstractions.dll", "$path/TerraCraft.dll" -Destination "build" -Force