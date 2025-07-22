import type { SupabaseClient } from "@supabase/supabase-js";

export async function insert(supabase: SupabaseClient, name: string) {
  const { data, error } = await supabase.from('instruments').insert([
    { name }
  ]);
  if (error) {
    console.error('Insert error:', error);
  } else {
    console.log('Inserted data:', data);
  }
}

export async function update(supabase: SupabaseClient, id: string, name: string) {
  const { data, error } = await supabase.from('instruments').update({ name }).eq('id', id);
  if (error) {
    console.error('Update error:', error);
  } else {
    console.log('Updated data:', data);
  }
}

export async function remove(supabase: SupabaseClient, id: string) {
  const { data, error } = await supabase.from('instruments').delete().eq('id', id);
  if (error) {
    console.error('Delete error:', error);
  } else {
    console.log('Deleted data:', data);
  }
}


