<#
.SYNOPSIS
�����ɊT�v�������܂�

.DESCRIPTION
�����ɐ����������܂�

.PARAMETER String
������n���܂���[

.PARAMETER Integer
����(integer)�n���܂���[

.PARAMETER Long
����(long)�n���܂���[

.PARAMETER Float
����(float)�n���܂���[

.PARAMETER Double
����(double)�n���܂���[

.PARAMETER Boolean
�^�U(boolean)�n���܂���[

.PARAMETER Array
�z��\���Ńp�����[�^�[��n�����ƂŔz��ɂł��܂���[


#>
param(
    [Parameter(Mandatory=$true, HelpMessage="������n���܂���[")]
    [string]$String,
    [Parameter(Mandatory=$true, HelpMessage="����(integer)�n���܂���[")]
    [int]$Integer,
    [Parameter(Mandatory=$true, HelpMessage="����(long)�n���܂���[")]
    [long]$Long,
    [Parameter(Mandatory=$true, HelpMessage="����(float)�n���܂���[")]
    [float]$Float,
    [Parameter(Mandatory=$true, HelpMessage="����(double)�n���܂���[")]
    [double]$Double,
    [Parameter(Mandatory=$true, HelpMessage="�^�U(boolean)�n���܂���[")]
    [boolean]$Boolean,
    [Parameter(Mandatory=$true, HelpMessage="�z��\���Ńp�����[�^�[��n�����ƂŔz��ɂł��܂���[")]
    [string[]]$Array
)

Write-Host ("String: " + $String)
Write-Host ("Integer: " + $Integer)
Write-Host ("Long: " + $Long)
Write-Host ("Float: " + $Float)
Write-Host ("Double: " + $Double)
Write-Host ("Boolean: " + $Boolean)
Write-Host ("Array: " + $Array)

