import "./LampAndLabel.css"

export type LampStatus = "on" | "off" | "blink";
export type LampAndLabelProps = {
  lampStatus: LampStatus;
  label: string;
};

export const LampAndLabel = (props: LampAndLabelProps) => {
  const lamp = (lampStatus: LampStatus) => {
    switch (lampStatus) {
      case "on":
        return <i class="lamp">🔴</i>;
        break;
      case "off":
        return <i class="lamp">⚫</i>;
        break;
      case "blink":
        return <i className="blink lamp">🔴</i>;
        break;
    }
  };

  return (
    <>
      <div class="LampAndLabel">
        {lamp(props.lampStatus)}
        <label className="label">{props.label}</label>
      </div>
    </>
  );
}

