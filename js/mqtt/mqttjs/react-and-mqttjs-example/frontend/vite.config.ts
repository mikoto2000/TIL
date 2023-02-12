import checker from 'vite-plugin-checker';
import react from '@vitejs/plugin-react'
import { defineConfig } from 'vite'

// https://vitejs.dev/config/
export default defineConfig({
  resolve: { alias: { mqtt: 'mqtt/dist/mqtt.js', }, },
  plugins: [react(),
     checker({ typescript: true }),
  ],
})