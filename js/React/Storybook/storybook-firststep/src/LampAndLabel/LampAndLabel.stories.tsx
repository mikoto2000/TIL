import type { Meta, StoryObj } from '@storybook/react';

import { LampAndLabel } from './LampAndLabel';

const meta: Meta<typeof LampAndLabel> = {
  component: LampAndLabel,
  argTypes: {
    lampStatus: {
      options: ["on", "off", "blink"],
      control: { type: 'select' },
    }
  }
};

export default meta;
type Story = StoryObj<typeof LampAndLabel>;

export const Primary: Story = {
  args: {
    lampStatus: "on",
    label: "Default label",
  }
};

