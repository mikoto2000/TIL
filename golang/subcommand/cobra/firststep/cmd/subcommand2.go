package cmd

import (
	"fmt"

	"github.com/spf13/cobra"
)

// subcommand2 のフラグ構造体定義
type Subcommand2Flags struct {
	strFlag     string
	boolFlag    bool
	intFlag     int
	float64Flag float64
}

// subcommand2 のフラグ実体
var flags Subcommand2Flags

func init() {
	rootCmd.AddCommand(subcommand2)

	subcommand2.Flags().StringVarP(
		&flags.strFlag,
		"str",    // ロングオプション名
		"s",      // ショートオプション名
		"デフォルト値", // デフォルト値
		"文字列フラグを渡せますよー", // 説明
	)
	subcommand2.Flags().BoolVarP(&flags.boolFlag, "bool", "b", false, "真偽値フラグを渡せますよー")
	subcommand2.Flags().IntVarP(&flags.intFlag, "int", "i", 0, "数値フラグを渡せますよー")
	subcommand2.Flags().Float64VarP(&flags.float64Flag, "float", "f", 0, "小数フラグを渡せますよー")
}

var subcommand2 = &cobra.Command{
	Use:     "subcommand2",
	Short:   "parse flags",
	Long:    `parse flags(Long description)`,
	Version: "1.0.0",
	Run: func(cmd *cobra.Command, args []string) {
		fmt.Printf("文字列フラグ: %s\n", flags.strFlag)
		fmt.Printf("真偽値フラグ: %b\n", flags.boolFlag)
		fmt.Printf("数値フラグ: %d\n", flags.intFlag)
		fmt.Printf("小数フラグ: %f\n", flags.float64Flag)
		fmt.Printf("フラグ構造体: %s\n", flags)
		fmt.Printf("Args %s\n", args)
	},
}
