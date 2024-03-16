$token = -join ((48..57) + (65..90) + (97..122) | Get-Random -Count 32 | % {[char]$_})

Write-Output "Generated Token: $token"