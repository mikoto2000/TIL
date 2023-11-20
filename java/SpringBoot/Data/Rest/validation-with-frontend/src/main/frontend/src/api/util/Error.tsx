import axios from "axios";

export type ApiErrorResponse = {
  errors: {
    targetName: string;
    message: string;
  }[];
};

export const extractApiErrorResponse = (error: unknown): ApiErrorResponse => {
  if (axios.isAxiosError(error) && error.response) {
    return (error.response.data as ApiErrorResponse);
  } else {
    throw error;
  }
};