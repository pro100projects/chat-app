export const HOST = 'http://localhost:8080';

export const BASE_PREFIX = '/api/v1';

export const API_ENDPOINTS = {
  REGISTER: BASE_PREFIX + '/auth/register',
  LOGIN: BASE_PREFIX + '/auth/login',
  USER_INFO: BASE_PREFIX + '/user',
  MESSAGES: BASE_PREFIX + '/chat/messages',
};
