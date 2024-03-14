package cmd

import (
	"fmt"

	"github.com/spf13/cobra"
)

var cowsay = &cobra.Command{
	Use:   "cowsay",
	Short: "Print cowsay",
	Long:  `Print cowsay(Long description)`,
	Run: func(cmd *cobra.Command, args []string) {
		fmt.Printf("Cowsay %s\n", args)
	},
}

func init() {
	rootCmd.AddCommand(cowsay)
}
