package main

import (
	"fmt"
	"os"

	"github.com/urfave/cli/v2"
)

var SUBCOMMANDS = []*cli.Command{
	{
		Name:    "cowsay",
		Aliases: []string{"cow"},
		Usage:   "print cowsay",
		Action: func(cCtx *cli.Context) error {
			fmt.Printf("cowsay %s", cCtx.Args())
			return nil
		},
	},
	{
		Name:    "subcommand2",
		Aliases: []string{"sc2"},
		Usage:   "parse flags",
		Flags: []cli.Flag{
			&cli.StringFlag{
				Name:    "str",
				Aliases: []string{"s"},
				Value:   "デフォルト値",
				Usage:   "文字列フラグを渡せますよー",
			},
			&cli.BoolFlag{
				Name:    "bool",
				Aliases: []string{"b"},
				Value:   false,
				Usage:   "真偽値フラグを渡せますよー",
			},
			&cli.IntFlag{
				Name:    "int",
				Aliases: []string{"i"},
				Value:   0,
				Usage:   "数値フラグを渡せますよー",
			},
			&cli.Float64Flag{
				Name:    "float",
				Aliases: []string{"f"},
				Value:   0,
				Usage:   "小数フラグを渡せますよー",
			},
		},
		Action: func(cCtx *cli.Context) error {
			fmt.Printf("文字列フラグ: %s\n", cCtx.String("str"))
			fmt.Printf("真偽値フラグ: %b\n", cCtx.Bool("bool"))
			fmt.Printf("数値フラグ: %d\n", cCtx.Int("int"))
			fmt.Printf("小数フラグ: %f\n", cCtx.Float64("float"))
			return nil
		},
	},
}

func main() {

	app := (&cli.App{
		Name:                   "urfave-cli",
		Usage:                  "urfave/cli subcommand example",
		Commands:               SUBCOMMANDS,
		Version:                "1.0.0",
		UseShortOptionHandling: true,
		Suggest:                true,
	})

	err := app.Run(os.Args)
	if err != nil {
		os.Exit(1)
	}
}
