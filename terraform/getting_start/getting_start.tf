variable access_key {}
variable secret_key {}
variable key_name {}

provider "aws" {
  access_key = "${var.access_key}"
  secret_key = "${var.secret_key}"
  region     = "us-east-1"
}

resource "aws_instance" "example" {
  ami           = "ami-2757f631"
  key_name      = "${var.key_name}"
  instance_type = "t2.micro"
  security_groups = ["ssh"]
}

