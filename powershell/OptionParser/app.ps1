<#
.SYNOPSIS
ここに概要を書きます

.DESCRIPTION
ここに説明を書きます

.PARAMETER String
文字列渡せますよー

.PARAMETER Integer
整数(integer)渡せますよー

.PARAMETER Long
整数(long)渡せますよー

.PARAMETER Float
小数(float)渡せますよー

.PARAMETER Double
小数(double)渡せますよー

.PARAMETER Boolean
真偽(boolean)渡せますよー

.PARAMETER Array
配列構文でパラメーターを渡すことで配列にできますよー


#>
param(
    [Parameter(Mandatory=$true, HelpMessage="文字列渡せますよー")]
    [string]$String,
    [Parameter(Mandatory=$true, HelpMessage="整数(integer)渡せますよー")]
    [int]$Integer,
    [Parameter(Mandatory=$true, HelpMessage="整数(long)渡せますよー")]
    [long]$Long,
    [Parameter(Mandatory=$true, HelpMessage="小数(float)渡せますよー")]
    [float]$Float,
    [Parameter(Mandatory=$true, HelpMessage="小数(double)渡せますよー")]
    [double]$Double,
    [Parameter(Mandatory=$true, HelpMessage="真偽(boolean)渡せますよー")]
    [boolean]$Boolean,
    [Parameter(Mandatory=$true, HelpMessage="配列構文でパラメーターを渡すことで配列にできますよー")]
    [string[]]$Array
)

Write-Host ("String: " + $String)
Write-Host ("Integer: " + $Integer)
Write-Host ("Long: " + $Long)
Write-Host ("Float: " + $Float)
Write-Host ("Double: " + $Double)
Write-Host ("Boolean: " + $Boolean)
Write-Host ("Array: " + $Array)

