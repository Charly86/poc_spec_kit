const JSON_HEADERS = {
  'Content-Type': 'application/json',
};

async function request(path, options = {}) {
  const response = await fetch(path, options);
  if (response.status === 204) {
    return null;
  }

  const contentType = response.headers.get('content-type') || '';
  const data = contentType.includes('application/json') ? await response.json() : null;

  if (!response.ok) {
    const message = data?.errors?.[0] || data?.message || 'No se pudo completar la accion.';
    throw new Error(message);
  }

  return data;
}

export function listTasks() {
  return request('/api/tasks');
}

export function createTask(payload) {
  return request('/api/tasks', {
    method: 'POST',
    headers: JSON_HEADERS,
    body: JSON.stringify(payload),
  });
}

export function getTask(id) {
  return request(`/api/tasks/${id}`);
}

export function updateTask(id, payload) {
  return request(`/api/tasks/${id}`, {
    method: 'PUT',
    headers: JSON_HEADERS,
    body: JSON.stringify(payload),
  });
}

export function completeTask(id) {
  return request(`/api/tasks/${id}/complete`, {
    method: 'PATCH',
  });
}

export function deleteTask(id) {
  return request(`/api/tasks/${id}`, {
    method: 'DELETE',
  });
}
